package com.thjug.duke;

import com.google.common.util.concurrent.MoreExecutors;
import com.thjug.duke.step.SearchStep;
import static java.util.Arrays.asList;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import org.jbehave.core.io.LoadFromClasspath;
import static org.jbehave.core.reporters.Format.*;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.PerStoriesWebDriverSteps;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverSteps;
import org.testng.annotations.Test;

/**
 *
 * @author nuboat
 */
public abstract class SeleniumStory extends ConfigurableEmbedder {

	private final WebDriverProvider duke = new PropertyWebDriverProvider();
	private final WebDriverSteps lifecycleSteps = new PerStoriesWebDriverSteps(duke); // or PerStoryWebDriverSteps(driverProvider)
	private final SeleniumContext context = new SeleniumContext();
	private final ContextView contextView = new LocalFrameContextView().sized(500, 100);

	public SeleniumStory() {
		if (lifecycleSteps instanceof PerStoriesWebDriverSteps) {
			configuredEmbedder().useExecutorService(MoreExecutors.newDirectExecutorService());
		}
	}

	public abstract String getStory();

	@Override
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
		return new SeleniumConfiguration()
				.useSeleniumContext(context)
				.useWebDriverProvider(duke)
				.useStepMonitor(new SeleniumStepMonitor(contextView, context, new SilentStepMonitor()))
				.useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useParameterControls(new ParameterControls().useDelimiterNamedParameters(true))
				.useStoryReporterBuilder(new StoryReporterBuilder()
						.withCodeLocation(codeLocationFromClass(embeddableClass))
						.withDefaultFormats()
						.withFormats(CONSOLE, TXT, HTML, XML));
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		Configuration configuration = configuration();
		return new InstanceStepsFactory(
            configuration, 
            lifecycleSteps,
            new SearchStep(duke)
            // Add step here.
            );
	}

	@Test
	@Override
	public void run() throws Throwable {
		final Embedder embedder = configuredEmbedder();

		embedder.runStoriesAsPaths(asList(getStory()));
	}

	public static class SameThreadEmbedder extends Embedder {

		public SameThreadEmbedder() {
			useExecutorService(MoreExecutors.newDirectExecutorService());
		}
	}
}
