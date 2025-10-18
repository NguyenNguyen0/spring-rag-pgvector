INSERT INTO rooms (
    name, code, capacity_adults, capacity_children, size_m2,
    refundable, floor, status, description
) VALUES
('Deluxe Room', 'DLX001', 2, 1, 35.5, TRUE, 3, 'AVAILABLE', 'Phòng Deluxe rộng rãi với view hồ bơi và ban công riêng.'),
('Suite Room', 'SUI002', 2, 2, 50.0, TRUE, 5, 'AVAILABLE', 'Phòng Suite sang trọng với phòng khách riêng và phòng tắm lớn.'),
('Standard Room', 'STD003', 2, 0, 25.0, FALSE, 2, 'OCCUPIED', 'Phòng tiêu chuẩn tiện nghi, phù hợp cho khách công tác.'),
('Family Room', 'FAM004', 3, 2, 60.0, TRUE, 4, 'AVAILABLE', 'Phòng gia đình rộng rãi với 2 giường đôi và khu vực sinh hoạt chung.'),
('Single Room', 'SGL005', 1, 0, 20.0, FALSE, 1, 'CLEANING', 'Phòng đơn nhỏ gọn, tiết kiệm và ấm cúng.'),
('Executive Suite', 'EXS006', 2, 1, 70.0, TRUE, 6, 'AVAILABLE', 'Phòng Executive Suite cao cấp với view toàn cảnh thành phố.'),
('Twin Room', 'TWN007', 2, 0, 28.0, FALSE, 2, 'OCCUPIED', 'Phòng Twin với 2 giường đơn, phù hợp cho bạn bè hoặc đồng nghiệp.'),
('Presidential Suite', 'PRS008', 4, 2, 120.0, TRUE, 8, 'MAINTENANCE', 'Phòng Tổng thống sang trọng nhất, có phòng ăn và jacuzzi riêng.'),
('Connecting Room', 'CON009', 2, 2, 55.0, TRUE, 3, 'AVAILABLE', 'Hai phòng thông nhau, phù hợp cho gia đình có trẻ nhỏ.'),
('Superior Room', 'SUP010', 2, 1, 30.0, FALSE, 2, 'AVAILABLE', 'Phòng Superior được thiết kế tinh tế, có bàn làm việc và sofa.');
