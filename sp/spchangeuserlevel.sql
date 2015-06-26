CREATE PROCEDURE spChangeUserLevel
@ID int,
@NewUserLevel int
AS
BEGIN
	UPDATE tblAccount
	SET AccountUserLevel = @NewUserLevel
	WHERE AccountID = @ID
END