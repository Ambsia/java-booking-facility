CREATE Procedure spGetMostUsedLocation
@N int output
AS
BEGIN
	Select BookingLocation, Count(BookingLocation) AS '@N'
	FROM tblBookings
	GROUP BY BookingLocation
	ORDER BY '@N' DESC
END
GO

Declare @g int = 0
EXecute spGetMostUsedLocation @N = @g