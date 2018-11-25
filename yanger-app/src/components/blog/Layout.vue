<template>
    <div class="wrapper">
        <b-header></b-header>
        <div class="blog-body-content">
		    <router-view></router-view>
	    </div>
        <b-footer></b-footer>
    </div>
</template>

<script>
    import bHeader from './Header.vue';
    import bFooter from './Footer';
    export default {
        data(){
            return {
            }
        },
        components: {
            bHeader, bFooter
        },
        created() {
        }
    }
</script>

<style scoped>
    .wrapper {
        overflow-y: auto;
        background-color: #efefef;
    }
    .blog-body-content {
		width: 1000px;
		min-height: 420px;
		margin: 0 auto; 
		margin-top:8px;
	}
</style>