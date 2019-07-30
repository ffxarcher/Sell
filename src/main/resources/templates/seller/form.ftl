<html>
<#include "../common/head.ftl">
<body>
<div id="wrapper" class="toggled">
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/form_login">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="sellerName" type="text" class="form-control""/>
                        </div>
                        <div class="form-group">
                            <label>password</label>
                            <input name="password" type="password" class="form-control"/>
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>


</div>
</body>
</html>