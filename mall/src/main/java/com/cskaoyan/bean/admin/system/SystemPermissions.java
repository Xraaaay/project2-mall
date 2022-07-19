package com.cskaoyan.bean.admin.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 权限
 *
 * @author Xrw
 * @date 2022/7/17 21:41
 */
@NoArgsConstructor
@Data
public class SystemPermissions {

    // 全部权限
    private List<First> systemPermissions;

    @NoArgsConstructor
    @Data
    public static class First {
        private String id;
        private String label;
        private List<Second> children;

        @NoArgsConstructor
        @Data
        public static class Second {
            private String id;
            private String label;
            private List<Third> children;

            @NoArgsConstructor
            @Data
            public static class Third {
                private String id;
                private String label;
                private String api;
            }
        }
    }
}
