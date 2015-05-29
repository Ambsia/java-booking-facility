USE [BookingSystem]
GO

/****** Object:  Table [dbo].[tblBookings]    Script Date: 29/05/2015 00:21:46 ******/
DROP TABLE [dbo].[tblBookings]
GO

/****** Object:  Table [dbo].[tblBookings]    Script Date: 29/05/2015 00:21:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[tblBookings](
	[BookingID] [int] IDENTITY(1,1) NOT NULL,
	[BookingDay] [char](12) NOT NULL,
	[BookingDate] [date] NOT NULL,
	[BookingStartTime] [time](7) NOT NULL,
	[BookingCollectionTime] [time](7) NOT NULL,
	[BookingLocation] [nvarchar](30) NOT NULL,
	[BookingHolder] [char](30) NOT NULL,
	[BookingEquipment] [nvarchar](100) NOT NULL,
	[BookingCompleted] [bit] NULL,
 CONSTRAINT [PK_tblBookings] PRIMARY KEY CLUSTERED 
(
	[BookingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


