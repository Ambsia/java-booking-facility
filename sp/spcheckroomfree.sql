USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spCheckIfRoomIsFree]    Script Date: 18/06/2015 15:43:55 ******/
DROP PROCEDURE [dbo].[spCheckIfRoomIsFree]
GO

/****** Object:  StoredProcedure [dbo].[spCheckIfRoomIsFree]    Script Date: 18/06/2015 15:43:55 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[spCheckIfRoomIsFree]
@Location nvarchar(50),
@BookingDate date,
@BookingStart time
AS
BEGIN
	Select BookingID
	from tblBookings
	WHERE BookingLocation = @Location AND
		  BookingDate = @BookingDate AND
		  BookingStartTime = @BookingStart
		  Group BY BookingID

END

GO


