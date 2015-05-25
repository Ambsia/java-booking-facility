USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spFindBooking]    Script Date: 25/05/2015 23:17:56 ******/
DROP PROCEDURE [dbo].[spFindBooking]
GO

/****** Object:  StoredProcedure [dbo].[spFindBooking]    Script Date: 25/05/2015 23:17:56 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE Procedure [dbo].[spFindBooking]
@Day char(12) = '',
@Date date = '01.01.70',
@BookingStartTime time = '00:00',
@BookingEndTime time = '00:00',
@BookingLocation nvarchar(30) = '',
@BookingHolder char(30) = '',
@BookingEquipment nvarchar(30) = ''
AS
BEGIN
	SELECT * FROM tblBookings
	WHERE (@Day = '' OR BookingDay = @Day) 
	AND (@Date = '01.01.70' OR BookingDate = @Date)
	AND (@BookingLocation = '' OR BookingLocation = @BookingLocation)
	AND (@BookingHolder = '' OR BookingHolder = @BookingHolder)
	AND (@BookingStartTime = '00:00' OR BookingStartTime = @BookingStartTime)
	AND (@BookingEndTime = '00:00' OR BookingCollectionTime = @BookingEndTime)
	AND (@BookingEquipment = '' OR BookingEquipment = @BookingEquipment) 
		ORDER BY BookingDate ASC, BookingStartTime ASC, BookingCollectionTime ASC
END
GO


