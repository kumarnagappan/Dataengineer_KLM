USE [aircraft_info]
GO

/****** Object:  Table [dbo].[CustomerFlightInfo]    Script Date: 22-11-2021 08:48:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[DelayedReasonKPI](
	[Year] [int] NULL,
	[Month] [int] NULL,
	[DayofMonth] [int] NULL,
	[DayofWeek] [int] NULL,
	[DepTime] [int] NULL,
	[CRSDepTime] [int] NULL,
	[ArrTime] [int] NULL,
	[CRSArrTime] [int] NULL,
	[UniqueCarrier] [nvarchar](max) NULL,
	[FlightNum] [int] NULL,
	[TailNum] [nvarchar](max) NULL,
	[CarrierDelay] [nvarchar](max) NULL,
	[WeatherDelay] [nvarchar](max) NULL,
	[NASDelay] [nvarchar](max) NULL,
	[SecurityDelay] [nvarchar](max) NULL,
	[LateAircraftDelay] [nvarchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


