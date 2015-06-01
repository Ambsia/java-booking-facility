CREATE Procedure spGetNBookingsCompleted
AS
BEGIN
	Select Count(BookingID) from tblBookings
	WHERE BookingCompleted = 1
END
GO


