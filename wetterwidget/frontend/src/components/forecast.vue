<template>
    <RouterLink :to="{ name: 'city', params: { weatherid: '' + wetter.openWeatherId  + '' } }" class="PreviewForecast">
        <div class="StadtNameForecast">
            Dortmund
        </div>
        <div class="gridforecast">
            <div class="div1"> Heute </div>
            <div class="div2"> Morgen </div>
            <div class="div3"> Übermorgen</div>
            <div class="div4">
                <div class="ForecastImage"></div>
            </div>
            <div class="div5">
                <div class="ForecastImage"></div>
            </div>
            <div class="div6">
                <div class="ForecastImage"></div>
            </div>

        </div>

    </RouterLink>
</template>

<script>

</script>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');

    .PreviewForecast {
        display: block;
        margin: 20px;
        width: 200px;
        height: 120px;
        background-color: #CCD0D8;
        border-radius: 20px;
        padding: 10px;
        overflow: hidden;
    }

    .StadtNameForecast {
        font-size: 18px;
        margin-left: -2px;
        overflow: hidden;
        color: #1463F3;
        text-align: center;
        font-family: 'Geologica', sans-serif;
    }

    .ForecastImage {
        height: 30px;
        width: 30px;
        background: blue;
        display: flex;
        justify-contend: center;
        align-items: center;
    }

    .gridforecast {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        grid-template-rows: repeat(2, 1fr);
        grid-column-gap: 0px;
        grid-row-gap: 0px;
        height: 80px;
        text-align: center;
        color: #84A4FC;
        margin-top: 10px;
        font-family: 'Geologica', sans-serif;
        font-size: 13px;
    }

    .div1 {
        grid-area: 1 / 1 / 2 / 2;
    }

    .div2 {
        grid-area: 1 / 2 / 2 / 3;
    }

    .div3 {
        grid-area: 1 / 3 / 2 / 4;
    }

    .div4 {
        grid-area: 2 / 1 / 3 / 2;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .div5 {
        grid-area: 2 / 2 / 3 / 3;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .div6 {
        grid-area: 2 / 3 / 3 / 4;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>