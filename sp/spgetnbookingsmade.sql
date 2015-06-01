CREATE Procedure spGetNOfBookingsMade
AS
BEGIN
	Select Count(BookingID) from tblBookings
END
GO


