<template>
<Breadcrumb>
    <BreadcrumbItem v-for="item in currentPath" :to="item.path" :key="item.name">{{ itemTitle(item) }}</BreadcrumbItem>
</Breadcrumb>
</template>

<script>
export default {
    name: 'breadcrumbNav',
    props: {
        currentPath: Array
    },
    methods: {
        itemTitle(item) {
            return item.title;
        }
    }
};
</script>
