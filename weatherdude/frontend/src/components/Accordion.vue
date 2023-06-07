<template>
    <b-card border-variant="light">
      <b-card-title class="accordion-title" @click="doExpand">
        {{ title }}
      </b-card-title>
      <hr class="accordion-dark" v-if="isExpanded"/>
      <div :class="accordionClasses">
        <div class="accordion-body">
          <div class="accordion-content">
              <slot/><!-- </slot> will be replaced with innerHTML by Vue.js -->
          </div>
        </div>
      </div>
    </b-card>
</template>

<script>
import Vue from 'vue';

export default {
    name: "accordion",
    props: {
        expanded: {
            type: Boolean,
            default: false
        },
        title: {
            type: String,
            default: "No-Title-Found"
        }
    },
    data() {
        Vue.nextTick(() => {
            if(this.isExpanded){
                this.$emit('opened')
                this.$emit('change', true)
            }
        });
        return {
            isExpanded: this.expanded
        }
    },
    methods: {
        doExpand: function() {
            if(this.isExpanded){
                this.isExpanded = false;
                this.$emit('closed')
            }
            else {
                this.isExpanded = true;
                this.$emit('opened')
            }

            this.$emit('change', this.isExpanded)
        }
    },
    computed: {
        accordionClasses: function() {
            return {
                'accordion-is-closed': !this.isExpanded
            };
        }
    }
}
</script>

<style>
    .accordion-dark {
        background-color: lightgray;
    }

    .accordion-title {
        cursor: pointer;
        margin-left: auto;
        margin-right: auto;
    }

    .accordion-body   {
        padding: 0;
        overflow: hidden;
        transition: 0.3s ease all;
    }

    .accordion-is-closed .accordion-body {
        max-height: 0;
    }

    .accordion-content {
        padding: 20px;
    }
</style>