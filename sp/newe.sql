USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spRemoveEquipment]    Script Date: 23/07/2015 09:03:33 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER Procedure [dbo].[spRemoveEquipment]
@EquipmentID int
AS
BEGIN
DELETE FROM tblBookings WHERE @EquipmentID = BookingEquipment
DELETE FROM tblEquipment WHERE EquipmentID = @EquipmentID
END
GO


ALTER Procedure spCheckUsedEquipment
@EquipmentID int,
@BookingsRelated int output
AS
BEGIN
SELECT @BookingsRelated = Count(BookingID) FROM tblBookings
WHERE @EquipmentID = tblBookings.BookingEquipment
END
GO
