USE [BookingSystem]
GO

/****** Object:  Table [dbo].[tblEquipment]    Script Date: 30/06/2015 14:51:09 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblEquipment](
	[EquipmentID] [int] IDENTITY(1,1) NOT NULL,
	[EquipmentName] [nvarchar](80) NOT NULL,
	[EquipmentDescription] [nvarchar](80) NULL,
	[EquipmentUsageStatistics] [int] NULL,
 CONSTRAINT [PK_tblEquipment] PRIMARY KEY CLUSTERED 
(
	[EquipmentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


