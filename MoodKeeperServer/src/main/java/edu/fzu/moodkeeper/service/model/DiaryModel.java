package edu.fzu.moodkeeper.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiaryModel {
    @SerializedName("Diary")
    public DiaryDTO diary;

    public static class DiaryDTO {
        public List<RecordListDTO> recordList;
        public boolean needSync;
        public String tableName;

        public static class RecordListDTO {
            public int weatherId;
            public int moodId;
            public long diaryDate;
            public String diaryName;
            public long anchor;
            public String diaryContent;
            public int state;
            public int localId;
            public int userId;
            public int categoryId;
            public String uuid;

            @Override
            public String toString() {
                return "RecordListDTO{" +
                        "weatherId=" + weatherId +
                        ", moodId=" + moodId +
                        ", diaryDate=" + diaryDate +
                        ", diaryName='" + diaryName + '\'' +
                        ", anchor=" + anchor +
                        ", diaryContent='" + diaryContent + '\'' +
                        ", state=" + state +
                        ", localId=" + localId +
                        ", userId=" + userId +
                        ", categoryId=" + categoryId +
                        ", uuid='" + uuid + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DiaryDTO{" +
                    "recordList=" + recordList +
                    ", needSync=" + needSync +
                    ", tableName='" + tableName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DiaryModel{" +
                "diary=" + diary +
                '}';
    }
}
