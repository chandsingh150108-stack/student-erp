import client from './client';
import { Department, Program, AcademicYear, Semester, Course, Student, Faculty, Section, DashboardStats, Announcement, Event, Scholarship, Book, Complaint, Backlog, FeeCategory, StudentFee } from './types';

export const departmentsApi = {
  list: () => client.get<Department[]>('/api/departments'),
  get: (id: number) => client.get<Department>(`/api/departments/${id}`),
  create: (data: Department) => client.post<Department>('/api/departments', data),
  update: (id: number, data: Department) => client.put<Department>(`/api/departments/${id}`, data),
  delete: (id: number) => client.delete(`/api/departments/${id}`),
};

export const programsApi = {
  list: () => client.get<Program[]>('/api/programs'),
  get: (id: number) => client.get<Program>(`/api/programs/${id}`),
  create: (data: Program) => client.post<Program>('/api/programs', data),
  update: (id: number, data: Program) => client.put<Program>(`/api/programs/${id}`, data),
  delete: (id: number) => client.delete(`/api/programs/${id}`),
};

export const academicYearsApi = {
  list: () => client.get<AcademicYear[]>('/api/academic-years'),
  create: (data: AcademicYear) => client.post<AcademicYear>('/api/academic-years', data),
  update: (id: number, data: AcademicYear) => client.put<AcademicYear>(`/api/academic-years/${id}`, data),
  delete: (id: number) => client.delete(`/api/academic-years/${id}`),
};

export const semestersApi = {
  list: () => client.get<Semester[]>('/api/semesters'),
  create: (data: Semester) => client.post<Semester>('/api/semesters', data),
  update: (id: number, data: Semester) => client.put<Semester>(`/api/semesters/${id}`, data),
  delete: (id: number) => client.delete(`/api/semesters/${id}`),
};

export const coursesApi = {
  list: () => client.get<Course[]>('/api/courses'),
  get: (id: number) => client.get<Course>(`/api/courses/${id}`),
  create: (data: Course) => client.post<Course>('/api/courses', data),
  update: (id: number, data: Course) => client.put<Course>(`/api/courses/${id}`, data),
  delete: (id: number) => client.delete(`/api/courses/${id}`),
};

export const studentsApi = {
  list: () => client.get<Student[]>('/api/students'),
  get: (id: number) => client.get<Student>(`/api/students/${id}`),
  search: (keyword: string) => client.get<Student[]>('/api/students/search', { params: { keyword } }),
  create: (data: Student) => client.post<Student>('/api/students', data),
  update: (id: number, data: Student) => client.put<Student>(`/api/students/${id}`, data),
  delete: (id: number) => client.delete(`/api/students/${id}`),
};

export const facultyApi = {
  list: () => client.get<Faculty[]>('/api/faculty'),
  get: (id: number) => client.get<Faculty>(`/api/faculty/${id}`),
  search: (keyword: string) => client.get<Faculty[]>('/api/faculty/search', { params: { keyword } }),
  create: (data: Faculty) => client.post<Faculty>('/api/faculty', data),
  update: (id: number, data: Faculty) => client.put<Faculty>(`/api/faculty/${id}`, data),
  delete: (id: number) => client.delete(`/api/faculty/${id}`),
};

export const sectionsApi = {
  list: () => client.get<Section[]>('/api/sections'),
  get: (id: number) => client.get<Section>(`/api/sections/${id}`),
  create: (data: Section) => client.post<Section>('/api/sections', data),
  update: (id: number, data: Section) => client.put<Section>(`/api/sections/${id}`, data),
  delete: (id: number) => client.delete(`/api/sections/${id}`),
};

export const dashboardApi = {
  getStats: () => client.get<DashboardStats>('/api/dashboard'),
};

export const announcementsApi = {
  list: () => client.get<Announcement[]>('/api/announcements'),
  recent: () => client.get<Announcement[]>('/api/announcements/recent'),
  create: (data: Announcement) => client.post<Announcement>('/api/announcements', data),
  delete: (id: number) => client.delete(`/api/announcements/${id}`),
};

export const eventsApi = {
  list: () => client.get<Event[]>('/api/events'),
  active: () => client.get<Event[]>('/api/events/active'),
  create: (data: Event) => client.post<Event>('/api/events', data),
  update: (id: number, data: Event) => client.put<Event>(`/api/events/${id}`, data),
  delete: (id: number) => client.delete(`/api/events/${id}`),
};

export const scholarshipsApi = {
  list: () => client.get<Scholarship[]>('/api/scholarships'),
  active: () => client.get<Scholarship[]>('/api/scholarships/active'),
  create: (data: Scholarship) => client.post<Scholarship>('/api/scholarships', data),
  delete: (id: number) => client.delete(`/api/scholarships/${id}`),
};

export const libraryApi = {
  books: () => client.get<Book[]>('/api/library/books'),
  createBook: (data: Book) => client.post<Book>('/api/library/books', data),
  updateBook: (id: number, data: Book) => client.put<Book>(`/api/library/books/${id}`, data),
  deleteBook: (id: number) => client.delete(`/api/library/books/${id}`),
  issuedBooks: () => client.get('/api/library/issued'),
  overdueBooks: () => client.get('/api/library/overdue'),
};

export const complaintsApi = {
  list: () => client.get<Complaint[]>('/api/complaints'),
  create: (data: Complaint) => client.post<Complaint>('/api/complaints', data),
  updateStatus: (id: number, status: string, resolution?: string) =>
    client.put(`/api/complaints/${id}/status`, null, { params: { status, resolution } }),
};

export const backlogsApi = {
  byStudent: (studentId: number) => client.get<Backlog[]>(`/api/backlogs/student/${studentId}`),
  create: (data: Backlog) => client.post<Backlog>('/api/backlogs', data),
};

export const feesApi = {
  categories: () => client.get<FeeCategory[]>('/api/fees/categories'),
  createCategory: (data: FeeCategory) => client.post<FeeCategory>('/api/fees/categories', data),
  studentFees: (studentId: number) => client.get<StudentFee[]>(`/api/fees/student/${studentId}`),
  pending: () => client.get<StudentFee[]>('/api/fees/pending'),
};

export const notificationsApi = {
  byUser: (userId: number) => client.get(`/api/notifications/user/${userId}`),
  unreadCount: (userId: number) => client.get(`/api/notifications/user/${userId}/unread-count`),
};
