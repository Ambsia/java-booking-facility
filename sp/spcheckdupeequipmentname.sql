USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spCheckForDuplicateUsername]    Script Date: 18/07/2015 19:00:08 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[spCheckForDuplicateEquipmentName]
@Username nvarchar(30),
@EntriesFound int output
AS
BEGIN
	Select @EntriesFound = Count(EquipmentName) from tblEquipment
	where @Username = EquipmentName
END
GO


