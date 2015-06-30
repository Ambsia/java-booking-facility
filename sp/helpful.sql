
INSERT INTO tblEquipment (EquipmentName, EquipmentUsageStatistics)
SELECT DISTINCT tblBookings.BookingEquipment,  Count(BookingEquipment) AS '@N'
	FROM tblBookings
	GROUP BY BookingEquipment
	ORDER BY '@N' DESC



UPDATE tblBookings 
SET BookingEquipment = i.EquipmentID
FROM (
    SELECT EquipmentID, EquipmentName 
    FROM tblEquipment) i
WHERE 
    EquipmentName = BookingEquipment