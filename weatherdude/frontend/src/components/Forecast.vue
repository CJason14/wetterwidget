<template>
<div class="info">
    <div class="topinfo">
        <p><strong>Heute</strong></p>
        <p>Morgen</p>
        <p>Woche</p>
    </div>
    <div class="bottominfo">
        <div class="view">
            <p>Jetzt</p>
            <div class="previewimage"></div>
            <p>Temp</p>
        </div>
        <div class="view">
            <p>2 Std</p>
            <div class="previewimage"></div>
            <p>Temp</p>
        </div>
        <div class="view">
            <p>4 Std</p>
            <div class="previewimage"></div>
            <p>Temp</p>
        </div>
        <div class="view">
            <p>6 Std</p>
            <div class="previewimage"></div>
            <p>Temp</p>
        </div>
    </div>
    <div class="infoplaceholder">
    </div>
</div>
</template>

<style>
.info {
    background-color: white;
    padding: 18px;
    border-radius: 50px 50px 0 0;
}
.topinfo {
    display: flex;
    justify-content: space-evenly;
}
.bottominfo {
    display: flex;
    justify-content: space-evenly;
}
.previewimage{
    min-height: 40px;
    min-width: 40px;
    background-color: pink;
}
.view{
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1 1 0;
    width: 0;
    height: 110px;
    background-color: #c2e1fe;
    margin: 10px;
    padding: 4px;
    border-radius: 25px;
    overflow: hidden;
}
.view p{
    padding: 0;
    margin: 2px;
}
.infoplaceholder{
 height: 100vh;
}
</style>