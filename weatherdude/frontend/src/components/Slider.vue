<template>
    <div style="max-width: 70%; margin: auto;">
        <b-input type="range" v-bind:min="slider_min" v-bind:max="slider_max_get()" v-model="slider_index"/>
        <output class="slider-label" :style="slider_label_styles" readonly>{{ slider_value_get() }}</output>
    </div>
</template>
<script>
import Vue from 'vue';

export default {
    name: "slider",
    props: {
        value: {
            type: Number,
            default: 0
        },
        min: {
            type: Number
        },
        max: {
            type: Number
        },
        values: {
            type: Array
        }
    },
    data(){
        var myMax = this.max;

        if(this.values){
            var myValues;
            if(this.values instanceof Array){
                myValues = this.values;
            }
            else if(this.values instanceof String){
                myValues = JSON.parse(this.values);
            }
            else {
                console.error("<Slider>: Invalid Values type!");
            }

            var l = myValues.length;
            if(myMax == null || l < myMax){
                myMax = l
            }
        }

        return {
            slider_index: this.value,
            slider_min: this.min,
            slider_max: myMax,
            slider_values: myValues,
            slider_label_styles: ""
        }
    },
    watch: {
        slider_index(index){
            const slider = event.target;

            Vue.nextTick(() => {
                var sliderWidth = slider.clientWidth;
                var label = slider.nextElementSibling;
                var labelWidth = label.clientWidth + 5;

                var maxWidth = sliderWidth - labelWidth;

                var delta = (index) / (this.slider_max_get() - this.slider_min_get());
                var pixels = delta * maxWidth;

                this.slider_label_styles = "margin-left:" + pixels + "px";
            });

            this.$emit("index-change", index);
            this.$emit("value-change", this.slider_value_get());
        }
    },
    methods: {
        slider_value_get: function(){
            var index = this.slider_index;
            if(index < this.slider_min){
                index = this.slider_min;
            }

            if(this.slider_values){
                return this.slider_values[index];
            }
            else {
                return index;
            }
        },
        slider_min_get: function(){
            if(! this.slider_min){
                return 0; //Default
            }

            return this.slider_min;
        },
        slider_max_get: function(){
            if(! this.slider_max){
                if(this.slider_values){
                    return this.slider_values.length - 1;
                }

                return 10; //Default
            }

            return this.slider_max;
        }
    }
}
</script>
<style>
    .slider-label {
        position: relative;
    }
</style>