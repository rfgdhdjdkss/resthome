import { defineStore } from "pinia";

export const definedUser = defineStore(
    {
        id: 'definedUser',
        state: () => {
            return {
                uid: '',
                username: '',
                token: '',
                nickname: '',
                permission: '',
                balance: '',
                headImgUrl: '',
            }
        },
        // 让这个token保持持久化
        persist: true
    }
)