<template>
    <div>
        <art-add v-if='$route.query.type == "1"'></art-add>
        <art-add-md v-if='$route.query.type == "2"'></art-add-md>
    </div>
</template>
<script>
import ArtAdd from './ArtAdd';
import ArtAddMd from './ArtAddMd';
export default {
    components: {
        ArtAdd,
        ArtAddMd
    }
}
</script>