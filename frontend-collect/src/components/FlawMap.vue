<template>
  <div class="flaw-map-container" :id="containerID">
    <el-empty description="没有报告" v-if="empty"></el-empty>
  </div>
</template>

<script>
import {getRandomId} from "@/utils/util";
import G6 from "@antv/g6";
import {ref} from "vue";

export default {
  name: "FlawMap",
  props: ['flawMapData'],
  data(){
    return {
      empty: false
    }
  },
  setup(prop, context){
    const graph = ref(null);
    const containerID = ref(getRandomId());
    const width = ref(800);
    const height = ref(400);
    const transformData = (flawMapData) => {
      console.log(flawMapData)
      let nodes = flawMapData.flawIds.map(id => {
/*
        const x = (mapNode.x * (Math.min(width.value, height.value) - 20) / 2) + width.value / 2;
        const y = (mapNode.y * (Math.min(width.value, height.value) - 20) / 2) + height.value / 2;
        //console.log(x)
        //console.log(y)*/
        return {
          id: "" + id,
          label: "#" + id,
          content: id
        }
      });
      let edges = [];
      for(let sourceIdx = 0; sourceIdx < flawMapData.flawIds.length; sourceIdx++){
        for(let targetIdx = sourceIdx + 1; targetIdx < flawMapData.flawIds.length; targetIdx++){
          const source = "" + flawMapData.flawIds[sourceIdx]
          const target= "" + flawMapData.flawIds[targetIdx]
          const similarity = flawMapData.similarityMatrix[sourceIdx][targetIdx]
          edges.push({
            source,
            target,
            label: '' + similarity,
            value: similarity,
            style: {
              lineWidth: similarity * 5,
            },
            stateStyles: {
              active: {
                strokeOpacity: 0.7,
                lineWidth: similarity * 5,
              }
            }
          })
        }
      }
      return {
        nodes,
        edges,
      }
    };
    G6.registerBehavior('activate-node', {
      getDefaultCfg() {
        return {
          multiple: true
        };
      },
      getEvents() {
        return {
          'node:mouseenter': 'onNodeMouseEnter',
          'node:mouseleave': 'onMouseLeave'
        };
      },

      onNodeMouseEnter(e) {
        const item = e.item;
        console.log('mouseenter')
        graph.value.setItemState(item, 'active', true);
        graph.value.getEdges().forEach(edge => {
          if(edge.getModel().target === item.getModel().id || edge.getModel().source === item.getModel().id){

            graph.value.setItemState(edge, 'active', true)

            graph.value.updateItem(edge, {
              labelCfg: {
                style: {
                  opacity: 1,
                }
              }
            })
          }
        })

      },
      onMouseLeave(e) {
        const graph = this.graph;
        const item = e.item;

        graph.setItemState(item, 'active', false);
        graph.getEdges().forEach(edge => {
          if(edge.getModel().target === item.getModel().id || edge.getModel().source === item.getModel().id){
            graph.setItemState(edge, 'active', false)
            graph.updateItem(edge, {
              labelCfg: {
                style: {
                  opacity: 0
                }
              }
            })
          }
        })
      }
    })
    const initMap = (mWidth, mHeight) => {
      const container = document.getElementById(containerID.value);
      width.value = mWidth || container.scrollWidth || width.value;
      height.value = mHeight || container.scrollHeight || height.value;
      //console.log(container.scrollWidth)
      graph.value = new G6.Graph({
        container: containerID.value,
        height: height.value,
        width: width.value,
        layout: {
          type: 'force',
          preventOverlap: true,
          edgeStrength: (edge) => {
            return edge.value
          },
          linkDistance: 200
        },

        modes: {
          default: ['drag-canvas', 'activate-node', 'zoom-canvas'],
        },
        defaultEdge: {
          labelCfg: {
            style: {
              opacity: 0
            }
          },
          style: {
            strokeOpacity: 0.7
          }
        },
        defaultNode: {
          size: 30,
          labelCfg: {
            style: {
              cursor: 'pointer'
            }
          },
          style: {
            cursor: 'pointer'
          }
        },
        edgeStateStyles: {
          active: {
            strokeOpacity: 1
          }
        },

      });
      graph.value.on('node:click', (e) => {
        context.emit('collect:select', e.item.getModel().content);
        //console.log(e.item.getModel())
      })

      graph.value.on('node:drag', function (e) {
        const forceLayout = graph.value.get('layoutController').layoutMethods[0];
        forceLayout.execute();
        const model = e.item.get('model');
        model.fx = e.x;
        model.fy = e.y;

      });
      graph.value.on('node:dragend', function (e) {
        e.item.get('model').fx = null;
        e.item.get('model').fy = null;
      });

    }
    return {
      graph,
      containerID,
      transformData,
      initMap
    }
  },
  watch: {
    flawMapData(val){
      console.log(val)
      if(!val || val.flawIds.length === 0){
        this.empty = true
        console.log('empty')
      }else{
        this.empty = false;
        this.graph.data(this.transformData(val))
        this.graph.render()
      }

    }
  }
}
</script>

<style scoped>
.flaw-map-container{
  height: 400px;
  width: 400px;
}

@media (max-width: 420px) {
  .flaw-map-container{
    width: 270px;
  }
}
</style>
