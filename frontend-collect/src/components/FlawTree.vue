<template>
  <div :id="containerID" style="width: 100%; min-height: 400px;"></div>
</template>

<script>
import {getRandomId} from "@/utils/util";
import G6 from '@antv/g6';
export default {
  name: "FlawTree",
  props: ['flawTreeData', 'currentFlaw'],
  data() {
    return {
      graph: undefined,
      containerID: getRandomId()
    }
  },
  methods: {
    convertTreeDataRec(treeNode){
      if(!treeNode.children){
       return undefined
      }
      const children = treeNode.children.map(this.convertTreeDataRec);
      return {
        id: `${treeNode.current.id}`,
        label: `#${treeNode.current.id}`,
        children,
        content: treeNode,
        height: 300,
        width: 600
      }

    },
    getTreeData(){
      return this.convertTreeDataRec(this.flawTreeData)
    },
    renderTree(data){
      const container = document.getElementById(this.containerID);
      this.width = container.scrollWidth || this.width;
      this.height = container.scrollHeight || this.height;

      this.graph = new G6.TreeGraph({
        height: this.height,
        width: this.width,
        container: this.containerID,
        defaultNode: {
          type: 'treeNode',
          anchorPoints: [
            [0, 0.5],
            [1, 0.5],
          ],
          size: 32,
          style: {
            lineWidth: 0.2,
            cursor: 'pointer',
          },
          labelCfg: {
            style: {
              fontSize: 8,
              cursor: 'pointer'
            },
          }
        },
        modes: {
          default: [],
        },
        defaultEdge: {
          type: 'cubic-horizontal',
          style: {
            lineWidth: 1.1
          }
        },
        layout: {
          type: 'compactBox',
          direction: 'LR',
          // 节点ID
          getId: function getId(d) {
            return d.id;
          },
          // 节点高度
          getHeight: function getHeight() {
            return 32;
          },
          // 节点宽度
          getWidth: function getWidth() {
            return 32;
          },
          // 节点垂直间隙
          getVGap: function getVGap() {
            return 20;
          },
          // 节点水平间隙
          getHGap: function getHGap() {
            return 100;
          },
        },
        nodeStateStyles: {
          hover: {
            shadowBlur: 75,
            shadowColor: '#cbcbcb'
          },
          select: {
            fill: 'red',
            fillOpacity: 0.1,
            stroke: 'red',
            strokeOpacity: 1
          }
        }
      });
      this.graph.on('node:click', (e) => {
        const model = e.item.getModel();
        this.$emit('select', model.content);
      });
      this.graph.on('node:mouseover', (e) =>{
        this.graph.setItemState(e.item, 'hover', true)
      });
      this.graph.on('node:mouseleave', (e) =>{
        this.graph.setItemState(e.item, 'hover', false)
      });

      if(!data){
        console.log('no data to render, skip render')
        return
      }
      this.graph.data(data);
      this.graph.render();
      this.graph.fitView();
    }
  },
  mounted() {
    setTimeout(() => {
      this.renderTree(this.getTreeData())
      if(this.currentFlaw){
        this.graph.setItemState(this.graph.findById(this.currentFlaw.id + ''), 'select', true)
      }
    }, 100)
  },
  watch: {
    flawTreeData(){
      //console.log('flawTreeData changed: ')
      //console.log(this.flawTreeData)
      if(this.graph){
        this.graph.changeData(this.getTreeData())
        this.graph.fitView()
        if(this.currentFlaw){
          this.graph.setItemState(this.graph.findById(this.currentFlaw.id + ''), 'select', true)
        }

      }
    },
    currentFlaw(newFlaw, oldFlaw){
      let oldItem = this.graph.findById(oldFlaw.id + '');
      let newItem = this.graph.findById(newFlaw.id + '');
      if(oldItem)this.graph.setItemState(oldItem, 'select', false)
      if(newItem)this.graph.setItemState(newItem, 'select', true)

    }
  }
}
</script>

<style scoped>

</style>
