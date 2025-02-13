import { defineStore } from 'pinia';

export const useHealthRecordStore = defineStore('healthRecord', {
    state: () => ({
        healthRecords: []
    }),
    actions: {
        setHealthRecords(records) {
            this.healthRecords = records;
        }
    }
});