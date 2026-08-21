export interface Department {
  id?: number;
  code: string;
  name: string;
  description?: string;
  active: boolean;
}

export interface Program {
  id?: number;
  department: Department;
  code: string;
  name: string;
  degreeType: string;
  durationYears: number;
  totalCredits: number;
  active: boolean;
}

export interface AcademicYear {
  id?: number;
  yearName: string;
  startDate: string;
  endDate: string;
  current: boolean;
}

export interface Semester {
  id?: number;
  semesterNumber: number;
  academicYear: AcademicYear;
  startDate: string;
  endDate: string;
  status: string;
}

export interface Course {
  id?: number;
  department: Department;
  code: string;
  name: string;
  description?: string;
  credits: number;
  courseType: string;
  lectureHours?: number;
  tutorialHours?: number;
  practicalHours?: number;
}

export interface Student {
  id?: number;
  user?: any;
  program: Program;
  rollNumber: string;
  registrationNumber?: string;
  firstName: string;
  lastName: string;
  dateOfBirth?: string;
  gender?: string;
  email?: string;
  phone?: string;
  admissionDate?: string;
  currentSemester?: number;
  address?: string;
  city?: string;
  state?: string;
  postalCode?: string;
  status: string;
}

export interface Faculty {
  id?: number;
  user?: any;
  department: Department;
  employeeNumber: string;
  firstName: string;
  lastName: string;
  email?: string;
  phone?: string;
  designation?: string;
  specialization?: string;
  joiningDate?: string;
  employmentStatus: string;
}

export interface Section {
  id?: number;
  program: Program;
  semester: Semester;
  academicYear: AcademicYear;
  name: string;
  capacity?: number;
}

export interface DashboardStats {
  totalStudents: number;
  totalFaculty: number;
  totalDepartments: number;
  totalPrograms: number;
  activeCourses: number;
  pendingFees: number;
  openComplaints: number;
  upcomingEvents: number;
  totalBooks: number;
  issuedBooks: number;
}

export interface Announcement {
  id?: number;
  title: string;
  content: string;
  createdDate?: string;
  expiryDate?: string;
  author?: string;
}

export interface Event {
  id?: number;
  name: string;
  description?: string;
  startDate: string;
  endDate: string;
  venue?: string;
  registrationDeadline?: string;
  active: boolean;
}

export interface Scholarship {
  id?: number;
  name: string;
  description?: string;
  amount: number;
  eligibilityInfo?: string;
  applicationDeadline?: string;
  active: boolean;
}

export interface Book {
  id?: number;
  isbn?: string;
  title: string;
  author: string;
  publisher?: string;
  category?: string;
  active: boolean;
}

export interface Complaint {
  id?: number;
  student?: Student;
  subject: string;
  description: string;
  submissionDate?: string;
  status: string;
  resolution?: string;
}

export interface Backlog {
  id?: number;
  student: Student;
  course: Course;
  semester: Semester;
  status: string;
  attemptNumber?: number;
}

export interface FeeCategory {
  id?: number;
  name: string;
  description?: string;
  active: boolean;
}

export interface StudentFee {
  id?: number;
  student: Student;
  feeStructure: any;
  totalAmount: number;
  amountPaid: number;
  remainingBalance: number;
  dueDate?: string;
  paymentStatus: string;
}
