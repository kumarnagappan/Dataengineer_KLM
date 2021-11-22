USE [aircraft_info]
GO

/****** Object:  Table [dbo].[CustomerFlightInfo]    Script Date: 22-11-2021 09:38:10 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CustomerFlightInfo](
	[Currency] [nvarchar](max) NULL,
	[Customer] [nvarchar](max) NULL,
	[Date to select] [nvarchar](max) NULL,
	[End of period] [datetime] NULL,
	[Start of period] [datetime] NULL,
	[Unit] [nvarchar](max) NULL,
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
	[ActualElapsedTime] [int] NULL,
	[CRSElapsedTime] [int] NULL,
	[AirTime] [int] NULL,
	[ArrDelay] [int] NULL,
	[DepDelay] [int] NULL,
	[Origin] [nvarchar](max) NULL,
	[Dest] [nvarchar](max) NULL,
	[Distance] [int] NULL,
	[TaxiIn] [int] NULL,
	[TaxiOut] [int] NULL,
	[Cancelled] [int] NULL,
	[CancellationCode] [nvarchar](max) NULL,
	[Diverted] [int] NULL,
	[CarrierDelay] [nvarchar](max) NULL,
	[WeatherDelay] [nvarchar](max) NULL,
	[NASDelay] [nvarchar](max) NULL,
	[SecurityDelay] [nvarchar](max) NULL,
	[LateAircraftDelay] [nvarchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


