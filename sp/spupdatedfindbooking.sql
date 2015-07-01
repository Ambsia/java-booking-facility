USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spFindBooking]    Script Date: 01/07/2015 14:22:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO





ALTER Procedure [dbo].[spFindBooking]
@Day char(12) = '',
@Date date = '01.01.70',
@BookingStartTime time = '00:00',
@BookingEndTime time = '00:00',
@BookingLocation nvarchar(30) = '',
@BookingHolder char(30) = '',
@BookingEquipment nvarchar(30) = ''
AS
BEGIN
	SELECT * from tblBookings 
	INNER JOIN tblEquipment ON tblEquipment.EquipmentID = tblBookings.BookingEquipment
	WHERE (@Day = '' OR BookingDay = @Day) 
	AND (@Date = '01.01.70' OR BookingDate = @Date)
	AND (@BookingLocation = '' OR BookingLocation = @BookingLocation)
	AND (@BookingHolder = '' OR BookingHolder = @BookingHolder)
	AND (@BookingStartTime = '00:00' OR BookingStartTime = @BookingStartTime)
	AND (@BookingEndTime = '00:00' OR BookingCollectionTime = @BookingEndTime) 
	AND (@BookingEquipment = '0' OR EquipmentID = @BookingEquipment) 
	AND (BookingCompleted = 0) 
		ORDER BY tblBookings.BookingDate ASC, tblBookings.BookingStartTime ASC, tblBookings.BookingCollectionTime ASC
END
GO

DECLARE @Day char(12) = '';
DECLARE @Date date = '01.01.70';
DECLARE @BookingStartTime time = '00:00';
DECLARE @BookingEndTime time = '00:00';
DECLARE @BookingLocation nvarchar(30) = '';
DECLARE @BookingHolder char(30) = '';
DECLARE @BookingEquipment nvarchar(30) = '8';

EXECUTE spFindBooking @Day, @Date, @BookingStartTime, @BookingEndTime, @BookingLocation, @BookingHolder, @BookingEquipment
