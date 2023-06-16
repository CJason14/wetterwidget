<template>
    <div  v-show="elementVisible"  class="popup" v-on:click="deleteelemt">
        <div class="popupcenter">
            {{ info }}
        </div>
        <div class="line">

        </div>
    </div>
</template>

<script>
    export default {
        props: {
            info: {
                type: String,
                required: true
            }
        },
        data() {
            return {
                elementVisible: true
            }
        },
        created() {
            setTimeout(() => this.elementVisible = false, 7000)
        }
    }
</script>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');

    .popup {
        position: fixed;
        top: 10px;
        right: 40px;
        overflow: hidden;
        width: 250px;
        height: 90px;
        background: #CCD0D8;
        border-radius: 10px;
        color: #1463F3;
        font-family: 'Geologica', sans-serif;
    }

    .popupcenter {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 90%;
    }

    .line {
        width: 100%;
        margin-top: 3px;
        height: 15px;
        background-color: #1463F3;
        animation: 7s linear 0s 1 slideInFromLeft;
    }

    @keyframes slideInFromLeft {
        0% {
            transform: translateX(0);
        }

        100% {
            transform: translateX(-100%);
        }
    }
</style>