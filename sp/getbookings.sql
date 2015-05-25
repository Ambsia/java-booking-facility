USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spGetAllBookings]    Script Date: 25/05/2015 23:24:07 ******/
DROP PROCEDURE [dbo].[spGetAllBookings]
GO

/****** Object:  StoredProcedure [dbo].[spGetAllBookings]    Script Date: 25/05/2015 23:24:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE Procedure [dbo].[spGetAllBookings]
AS
BEGIN
	Select BookingID, BookingDay, BookingDate, BookingStartTime, BookingCollectionTime, BookingLocation, BookingHolder, BookingEquipment from tblBookings
	ORDER BY BookingDate ASC, BookingStartTime ASC, BookingCollectionTime ASC
END


GO


