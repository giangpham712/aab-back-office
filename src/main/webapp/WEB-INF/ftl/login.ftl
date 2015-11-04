<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Log in</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link rel="stylesheet" type="text/css" href="/bower_components/jquery-icheck/skins/square/blue.css">

</head>
<body class="login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="#"><b>AAB</b>Plastic</a>
    </div><!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>
        <form action="/login" method="POST">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" name="username" id="username" placeholder="Username" autofocus/>
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" name="password" id="password" placeholder="Password"/>
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input type="checkbox" name="remember-me"> &nbsp; Remember Me
                        </label>
                    </div>
                </div><!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                </div><!-- /.col -->
            </div>
        </form>

        <a href="#">I forgot my password</a><br>

    </div><!-- /.login-box-body -->
</div><!-- /.login-box -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>
<script src="/bower_components/jquery-icheck/icheck.min.js"></script>
<script>
    $(function () {
        $('input[type=checkbox').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</body>
</html>