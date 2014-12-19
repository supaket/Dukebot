Scenario: Should has Banyan Tree Hangzhou at Hangzhou city on Feb 01, 2015

Given ผู้ใช้ไปที่ http://www.agoda.com

When ผู้ใช้พิมพ์ <city> ที่ช่อง SearchInput
And ผู้ใช้เลือกข้อความ <checkin_month> ที่ CheckInMonthYear ตัวเลือก
And ผู้ใช้เลือกข้อความ <checkin_day> ที่ CheckInDay ตัวเลือก
And ผู้ใช้เลือกข้อความ <night> ที่ NightCount ตัวเลือก
And ผู้ใช้กดปุ่มคลาส submit
Then ระบบโชว์หน้าจอ /pages/agoda/default/DestinationSearchResult.aspx
And ระบบโชว์โรงแรม <hotelname>
And หยุดรอ 10 วินาที

Examples: 
|city|hotelname|checkin_month|checkin_day|night|
|Hangzhou|Banyan Tree Hangzhou|Feb, 2015|Sun 01|1|
