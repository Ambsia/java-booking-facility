USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spInsertBooking]    Script Date: 28/05/2015 23:22:03 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


ALTER Procedure [dbo].[spInsertBooking]
@Day char(12) ='',
@Date date = '2015/12/12',
@BookingStartTime time = '8:00:00',
@BookingEndTime time= '8:00:00',
@BookingLocation nvarchar(30) = '',
@BookingHolder char(30) = '',
@BookingEquipment nvarchar(30) = '',
@BookingCompleted bit,
@BookingID int output
AS
BEGIN
	INSERT INTO tblBookings VALUES(@Day, @Date, @BookingStartTime, @BookingEndTime, @BookingLocation,@BookingHolder,@BookingEquipment, @BookingCompleted)
	SET @BookingID = @@IDENTITY
END

GO


