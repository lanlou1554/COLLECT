
/**
 * 使用方法：
 * 如果是根据后端返回结果来唤出提示框可以直接使用：
 * this.$emit(SHOW_ALERT, res);
 *
 * 如果是根据其他情况唤出提示框可以使用：
 * this.$emit(SHOW_ALERT, {
      duration: 1000,
      msg: 'test',
      type: 'success'
    })
 * **/
export const SHOW_ALERT = 'collect:alert'; //显示alert



/**
 * 使用方法：
 * 在子组件的mounted方法中发出这个事件，参数是一个数字，代表页面最大宽度，单位px
     mounted() {
        this.$emit(SET_MAX_WIDTH, 1600)
      }
 */
export const SET_MAX_WIDTH = 'collect:set-max-width' //设置页面最大宽度



