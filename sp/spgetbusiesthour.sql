ALTeR Procedure spGetBusiestHours
@N int output,
@G int output
AS
BEGIN
	Select BookingStartTime, BookingCollectionTime, Count(BookingStartTime) AS '@N', Count(BookingCollectionTime) AS '@G'
	FROM tblBookings
	GROUP BY BookingCollectionTime, BookingStartTime
	ORDER BY '@G' DESC, '@N' DESC
END
GO

Declare @g int = 0
Declare @n int = 0
EXecute spGetBusiestHours @N = @g, @G = @n