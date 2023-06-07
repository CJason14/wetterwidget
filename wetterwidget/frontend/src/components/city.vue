<template>
    <a class="Preview">
    <div class="Info">
        <div class="StadtName">
        Dortmund
        </div>
        <div class="StadtInfo">
        Niederschlag: 10
        <br>
        Luftfeuchtigkeit: 10
        </div>
    </div>
    <div class="Inforight">
        20°
    </div>

    </a>
</template>

<script>

</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');
.Preview{
    display: block;
    margin: 20px;
    width: 200px;
    height: 70px;
    background-color: #CCD0D8;
    border-radius: 20px;
    padding: 10px;
    overflow: hidden;
}
.Info{
    position: relative;
    top: 10px;
    left: 10px;

}
.Inforight{
    position: relative;
    left: 137px;
    bottom: 36px;
    font-size: 35px;
    font-weight: bold;
    color: #1463F3;
    font-family: 'Geologica', sans-serif;
}

.StadtName{
    font-size: 22px;
    margin-left: -2px;
    max-width: 110px;
    overflow: hidden;
    color: #1463F3;
    font-family: 'Geologica', sans-serif;
}

.StadtInfo{
    color: grey;
    font-size: 10px;
}
</style>
