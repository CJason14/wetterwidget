<template>
    <div class="tab-view-parent-container">
        <div v-if="children" class="tab-view-parent-btn-container">
            <button v-for="child in children" :key="child.id" :class="btnClasses(child.id)" @click="onTabClick(child.id)">
                {{ child.name }}
            </button>
        </div>
        <slot></slot>
    </div>
</template>
<script>

export default {
    name: "tab-view-parent",
    data() {
        return {
            children: [],
            selected: null
        };
    },
    mounted() {
        var nodes = this.$slots.default;
        
        var selectedId;

        //remove all children which are not 'tab-view'
        //and add tab - buttons
        nodes.forEach(node => {
            if(!node.tag.endsWith('tab-view')){
                node.elm.remove();
            }
            else {
                var index = this.children.length;
                var child = node.child;

                if(child.open){
                    selectedId = index;
                }

                this.children.push({
                    id: index,
                    name: child.title,
                    node: child
                });
            }
        });

        if(selectedId){
            this.selectTab(selectedId);
        }
        else {
            this.selectTab(0);
        }
    },
    methods: {
        btnClasses(id) {
            var selected = (id == this.selected);

            return {
                'tab-view-parent-btn': true,
                'tab-view-parent-btn-pressed': selected
            };
        },
        selectTab(id) {
            //console.log("Select: pre: ", this.selected, " now: ", id);

            if(this.selected != undefined){
                //console.log("Deselect: ", this.selected);
                this.children[this.selected].node.open = false;
            }

            this.selected = id;
            this.children[id].node.open = true;
        },
        onTabClick(id) {
            this.selectTab(id);
        }
    }
}
</script>
<style>
    .tab-view-parent-container {
        border: 1px solid black;
        background-color: rgb(240, 240, 240);
    }

    .tab-view-parent-btn-container {
        background-color: white;
    }


    .tab-view-parent-btn {
        outline: none;
        border: none;
        border-radius: 0;
        background-color: white;
    }

    .tab-view-parent-btn:focus {
        box-shadow: none;
    }

    .tab-view-parent-btn-pressed {
        background-color: rgb(240, 240, 240);
    }

</style>