<template>
    <a class="PreviewTemp">
        <div class="StadtNameForecast">
            Dortmund
        </div>
        <div class="flexvertical">
            <div class="flexhorizontal">
                <div class="flexvertical textaligncenter small">
                    Gefühlt:
                    <br>
                    10°
                </div>
                <div class="flexvertical textaligncenter small">
                    Aktuell:
                    <br>
                    10°
                </div>
            </div>
            <div class="flexhorizontal">
                <div class="flexvertical textaligncenter small">
                    Min:
                    <br>
                    10°
                </div>
                <div class="flexvertical textaligncenter small">
                    Max:
                    <br>
                    10°
                </div>
            </div>
        </div>

    </a>
</template>

<script>

</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');

.small{
    font-family: 'Geologica', sans-serif;
    font-size: 14px;
    width: 80px;
    overflow:hidden;
    margin-top:4px;
    color: #84A4FC;
}
.textaligncenter{
    text-align: center;
}

.flexvertical{
    display: flex;
    flex-direction: column;
}
.flexhorizontal{
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;

}
.pink{
    background: pink;
    min-width: 20px;
    height: 20px;
    margin: 5px;
}

.PreviewTemp{
    display: block;
    margin: 20px;
    width: 200px;
    height: 102px;
    background-color: #CCD0D8;
    border-radius: 20px;
    padding: 10px;
    overflow: hidden;
}
</style>