CREATE Procedure spCompleteBooking
@BookingID int
AS
BEGIN
	UPDATE tblBookings
	SET BookingCompleted = @BookingID
	WHERE BookingID = @BookingID
END

GO

