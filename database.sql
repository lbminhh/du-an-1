USE [master]
GO


/****** Object:  Database [du_an_1]    Script Date: 11/03/2024 4:52:19 PM ******/
CREATE DATABASE [du_an_1]

ALTER DATABASE [du_an_1] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [du_an_1].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [du_an_1] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [du_an_1] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [du_an_1] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [du_an_1] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [du_an_1] SET ARITHABORT OFF 
GO
ALTER DATABASE [du_an_1] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [du_an_1] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [du_an_1] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [du_an_1] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [du_an_1] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [du_an_1] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [du_an_1] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [du_an_1] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [du_an_1] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [du_an_1] SET  ENABLE_BROKER 
GO
ALTER DATABASE [du_an_1] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [du_an_1] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [du_an_1] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [du_an_1] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [du_an_1] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [du_an_1] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [du_an_1] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [du_an_1] SET RECOVERY FULL 
GO
ALTER DATABASE [du_an_1] SET  MULTI_USER 
GO
ALTER DATABASE [du_an_1] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [du_an_1] SET DB_CHAINING OFF 
GO
ALTER DATABASE [du_an_1] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [du_an_1] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [du_an_1] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [du_an_1] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'du_an_1', N'ON'
GO
ALTER DATABASE [du_an_1] SET QUERY_STORE = OFF
GO
USE [du_an_1]
GO
/****** Object:  Table [dbo].[account]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[employee_id] VARCHAR(10) NOT NULL,
	[username] [varchar](50) NULL,
	[password] [varchar](50) NULL,
	[status] [bit] NULL,
	[role_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[employee_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[bill_detail]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[bill_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[bill_id] VARCHAR(10) NULL,
	[product_id] VARCHAR(10) NOT NULL,
	[size_id] [int] NULL,
	[color_id] [int] NULL,
	[material_id] [int] NULL,
	[sole_id] [int] NULL,
	[quantity] [int] NULL,
	[total_money] [money] NULL,
	[time_create] [timestamp] NOT NULL,
	[status] [bit] NULL,
	[reduce_money] [money] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[bills]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[bills](
	[id] VARCHAR(10) NOT NULL,
	[customer_id] VARCHAR(10) NOT NULL,
	[time_create] [timestamp] NOT NULL,
	[status] [bit] NULL,
	[phone] [varchar](55) NULL,
	[total_money] [money] NULL,
	[voucher_id] [int] NULL,
	[payment_id] [int] NULL,
	[employee_id] VARCHAR(10) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[brand]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[brand](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[brand_name] [nvarchar](255) NOT NULL,
	[status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[category]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [nvarchar](255) NOT NULL,
	[category_status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[color]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[color](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[color_name] [nvarchar](255) NOT NULL,
	[status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[id] VARCHAR(10) NOT NULL,
	[full_name] [varchar](255) NULL,
	[address] [varchar](255) NULL,
	[phone_number] [varchar](50) NULL,
	[email] [varchar](50) NULL,
	[gender] [bit] NULL,
	[time_create] [timestamp] NOT NULL,
	[is_bought] [bit] NULL,
	[status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[discount]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[discount](
	[id] VARCHAR(10) NOT NULL,
	[discount_name] [nvarchar](255) NOT NULL,
	[discount_value] [money] NULL,
	[start_discount_time] [date] NOT NULL,
	[end_discount_time] [date] NOT NULL,
	[status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[employee]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[employee](
	[id] VARCHAR(10) NOT NULL,
	[full_name] [nvarchar](255) NULL,
	[date_of_birth] [date] NULL,
	[address] [nvarchar](255) NULL,
	[phone_number] [varchar](50) NULL,
	[time_create] [timestamp] NOT NULL,
	[status] [bit] NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[material]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[material](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[material_name] [nvarchar](255) NOT NULL,
	[status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_detail]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] VARCHAR(10) NOT NULL,
	[price] [money] NOT NULL,
	[quantity] [int] NOT NULL,
	[time_create] [timestamp] NOT NULL,
	[color_id] [int] NOT NULL,
	[size_id] [int] NOT NULL,
	[material_id] [int] NOT NULL,
	[sole_id] [int] NOT NULL,
	[status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[products]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[id] VARCHAR(10) NOT NULL,
	[product_name] [nvarchar](255) NOT NULL,
	[description] [nvarchar](max) NULL,
	[category_id] [int] NOT NULL,
	[brand_id] [int] NOT NULL,
	[status] [bit] NOT NULL,
	[discount_id] VARCHAR(10) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [nvarchar](255) NULL,
	[status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[size_name] [varchar](10) NOT NULL,
	[status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sole_shoes]    Script Date: 11/03/2024 4:52:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sole_shoes](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sole_name] [nvarchar](255) NOT NULL,
	[status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account]  WITH CHECK ADD FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[bill_detail]  WITH CHECK ADD FOREIGN KEY([bill_id])
REFERENCES [dbo].[bills] ([id])
GO
ALTER TABLE [dbo].[bill_detail]  WITH CHECK ADD FOREIGN KEY([color_id])
REFERENCES [dbo].[color] ([id])
GO
ALTER TABLE [dbo].[bill_detail]  WITH CHECK ADD FOREIGN KEY([material_id])
REFERENCES [dbo].[material] ([id])
GO
ALTER TABLE [dbo].[bill_detail]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[products] ([id])
GO
ALTER TABLE [dbo].[bill_detail]  WITH CHECK ADD FOREIGN KEY([size_id])
REFERENCES [dbo].[size] ([id])
GO
ALTER TABLE [dbo].[bill_detail]  WITH CHECK ADD FOREIGN KEY([sole_id])
REFERENCES [dbo].[sole_shoes] ([id])
GO
ALTER TABLE [dbo].[bills]  WITH CHECK ADD FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[bills]  WITH CHECK ADD FOREIGN KEY([employee_id])
REFERENCES [dbo].[employee] ([id])
GO
ALTER TABLE [dbo].[employee]  WITH CHECK ADD FOREIGN KEY([id])
REFERENCES [dbo].[account] ([employee_id])
GO
ALTER TABLE [dbo].[product_detail]  WITH CHECK ADD FOREIGN KEY([color_id])
REFERENCES [dbo].[color] ([id])
GO
ALTER TABLE [dbo].[product_detail]  WITH CHECK ADD FOREIGN KEY([material_id])
REFERENCES [dbo].[material] ([id])
GO
ALTER TABLE [dbo].[product_detail]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[products] ([id])
GO
ALTER TABLE [dbo].[product_detail]  WITH CHECK ADD FOREIGN KEY([size_id])
REFERENCES [dbo].[size] ([id])
GO
ALTER TABLE [dbo].[product_detail]  WITH CHECK ADD FOREIGN KEY([sole_id])
REFERENCES [dbo].[sole_shoes] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([brand_id])
REFERENCES [dbo].[brand] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [dbo].[category] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([discount_id])
REFERENCES [dbo].[discount] ([id])
GO
USE [master]
GO
ALTER DATABASE [du_an_1] SET  READ_WRITE 
GO
