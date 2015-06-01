CREATE Procedure spGetMostUsedEquipment
@N int output
AS
BEGIN
	Select BookingEquipment, Count(BookingEquipment) AS '@N'
	FROM tblBookings
	GROUP BY BookingEquipment
	ORDER BY '@N' DESC
END
GO

Declare @g int = 0
EXecute spGetMostUsedEquipment @ = @g