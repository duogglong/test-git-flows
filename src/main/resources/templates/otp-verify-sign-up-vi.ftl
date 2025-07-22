<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email OTP</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            background-color: #ffffff;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .header {
            text-align: center;
            background-color: #C72545;
            padding: 20px;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }

        .header h1 {
            margin: 0;
            color: #ffffff;
        }

        .content {
            padding: 20px;
        }

        .content p {
            font-size: 16px;
            line-height: 1.5;
            color: #333333;
        }

        .otp {
            display: block;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            margin: 20px 0;
            color: #C72545;
        }

        .footer {
            text-align: center;
            padding: 20px;
            background-color: #f4f4f4;
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
        }

        .footer p {
            font-size: 14px;
            color: #777777;
            margin: 0;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="header">
        <h1>Reta</h1>
    </div>
    <div class="content">
        <p>Chào bạn,</p>
        <p>Chúng tôi đã nhận được yêu cầu xác thực OTP của bạn. Mã OTP của bạn là:</p>
        <span class="otp">${otp}</span>
        <p>Vui lòng nhập mã OTP này để hoàn tất quá trình xác thực.</p>
        <p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>
    </div>
    <div class="footer">
        <p>Reta - Tạo dựng tương lai của bạn</p>
    </div>
</div>
</body>

</html>
