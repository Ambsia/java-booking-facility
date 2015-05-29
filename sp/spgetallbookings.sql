USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spGetAllBookings]    Script Date: 28/05/2015 23:48:12 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



ALTER Procedure [dbo].[spGetAllBookings]
AS
BEGIN
	Select BookingID, BookingDay, BookingDate, BookingStartTime, BookingCollectionTime, BookingLocation, BookingHolder, BookingEquipment, BookingCompleted from tblBookings
	ORDER BY BookingDate ASC, BookingStartTime ASC, BookingCollectionTime ASC
END


GO


