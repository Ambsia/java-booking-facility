CREATE PROCEDURE spChangePassword
@ID int,
@NewPassword nvarchar(50)
AS
BEGIN
	UPDATE tblAccount
	set AccountSaltedPassword = @NewPassword
	WHERE AccountID = @ID
END