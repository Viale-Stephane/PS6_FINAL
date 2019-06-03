export interface Appointment {
    id?: number;
    title?: string;
    desc?: string;
    date?: Date;
    startTime?: string;
    endTime?: string;
    places?: number;
    teacherID?: number;
    studentsID?: number[];
}
