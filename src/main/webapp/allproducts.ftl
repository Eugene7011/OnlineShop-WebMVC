<#import "/spring.ftl" as spring/>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- allproducts_shop CSS -->
    <title>Online-Shop</title>
    <link rel="stylesheet" href="allproducts.css">

    <!-- fonts from fonts.google.com-->
    <script src="https://kit.fontawesome.com/53bc8d5a3b.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css2?family=Yeseva+One&display=swap" rel="stylesheet">
</head>
<body>
<!-- Navigation bar-->
<nav class="navbar navbar-expand-sm navbar-dark bg-black">
    <div class="container">
        <a class="navbar-brand">Brand</a>
        <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto">
                <li class="nav-element">
                    <a class="nav-link">Home</a>
                </li>
                <li class="nav-element">
                    <a href="#" class="nav-link">About us</a>
                </li>
                <li class="nav-element">
                    <a href="/products" class="nav-link">Products</a>
                </li>
                <li class="nav-element">
                    <a class="nav-link">Services</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown">
                        Admin
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="/products">Products</a></li>
                        <li><a class="dropdown-item" href="#">Services</a></li>
                    </ul>
                </li>
            </ul>
            <li class="nav-element">
                <a href="/products/cart" class="nav-link"><i class="fas fa-shopping-cart fa-2x"></i></a>
            </li>

        </div>
    </div>
</nav>
<!-- End of Navigation bar-->

<!-- Section of search-->
<section class="search py-5">
    <div class="container">
        <div class="row">
            <div class="text-center mx-auto col-sm-6">
                <form class="d-flex" action="/products/search" method="GET">
                    <input class="form-control me-2" type="search"  name="searchText" placeholder="Search product">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </div>
</section>
<!-- End of search Section-->

<!-- Section of products-->
<a href="/products/add" ><button type="button" class="btn btn-primary" style=position:absolute;left:80%; >CREATE PRODUCT</button></a><br>
<section class="products">
    <div class="container">
        <div class="row">
            <div class="col-10 text-center mx-auto col-sm-6">
                <h1 class="product-title">Available products</h1>
            </div>
        </div>

        <table class="table table-striped">
            <thead class="thead-light" align="center" bgcolor="	#d3d3d3" >
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Price</th>
                <th scope="col">Creation Date</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <#list products as product>
            <tr align="center" width="50%" border="1" >
                <td >${product.getId()}</td>
                <td>${product.getName()}</td>
                <td>${product.getPrice()}</td>
                <td>${product.getCreationDate()}</td>
                <td>
                    <a href="/products/update" method="POST" ><button type="button" class="btn btn-success" >EDIT</button></a>
                    <a href="/products/delete" ><button type="button" class="btn btn-danger">DELETE</button></a>
                    <a href="/products/cart/add" ><button type="button" class="btn btn-success">ADD TO CART</button></a>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>

        <div class="row product-element">
        </div>
    </div>
</section>
</body>
</html>
<!-- End of product`s sector -->

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>
