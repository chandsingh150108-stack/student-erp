import React, { useEffect, useState } from 'react';
import { libraryApi } from '../api/endpoints';
import { Book } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Library: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ title: '', author: '', isbn: '', publisher: '', category: '', active: true });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();
  const load = () => libraryApi.books().then(r => setBooks(r.data));
  useEffect(() => { load(); }, []);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await libraryApi.updateBook(editId, form); } else { await libraryApi.createBook(form); }
    setShowForm(false); setEditId(null); load();
  };
  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Library</h1><p className="page-subtitle">{books.length} books</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ title: '', author: '', isbn: '', publisher: '', category: '', active: true }); }}>+ Add Book</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Book</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Title</label><input value={form.title} onChange={e => setForm({...form, title: e.target.value})} required /></div>
              <div className="form-group"><label>Author</label><input value={form.author} onChange={e => setForm({...form, author: e.target.value})} required /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>ISBN</label><input value={form.isbn || ''} onChange={e => setForm({...form, isbn: e.target.value})} /></div>
              <div className="form-group"><label>Category</label><input value={form.category || ''} onChange={e => setForm({...form, category: e.target.value})} /></div>
            </div>
            <div className="form-group"><label>Publisher</label><input value={form.publisher || ''} onChange={e => setForm({...form, publisher: e.target.value})} /></div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button></div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>Title</th><th>Author</th><th>ISBN</th><th>Category</th><th>Publisher</th>{isAdmin && <th>Actions</th>}</tr></thead>
          <tbody>
            {books.map(b => (
              <tr key={b.id}>
                <td><strong>{b.title}</strong></td>
                <td>{b.author}</td>
                <td>{b.isbn}</td>
                <td>{b.category}</td>
                <td>{b.publisher}</td>
                {isAdmin && <td><button className="btn-sm" onClick={() => { setForm(b); setEditId(b.id!); setShowForm(true); }}>Edit</button><button className="btn-sm danger" onClick={() => { if(confirm('Delete?')) { libraryApi.deleteBook(b.id!).then(load); } }}>Delete</button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
export default Library;
