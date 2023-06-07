<template>
    <div>
        <slot v-if="open"/>
    </div>
</template>
<script>
export default {
    name: "tab-view",
    props: {
        selected: {
            type: Boolean,
            default: false
        },
        title: {},

    },
    data() {
        return {
            open: this.selected
        }
    },
    /*watch: {
        open() {
            console.log(this.title, "-> ", this.open);
        }
    }*/
}
</script>
<style>



</style>