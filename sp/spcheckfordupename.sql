USE [BookingSystem]
GO

/****** Object:  StoredProcedure [dbo].[spCheckForDuplicateUsername]    Script Date: 26/06/2015 00:18:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

Create PROCEDURE [dbo].[spCheckForDuplicateUsername]
@Username nvarchar(30),
@EntriesFound int output
AS
BEGIN
	Select @EntriesFound = Count(AccountUsername) from tblAccount
	where @Username = AccountUsername
END
GO


