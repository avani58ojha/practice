service UserService {
    entity UserProducts {
        key ID : Integer;
        title  : String(111);
        descr  : String(1111);
    }
    entity UserData {
        key ID : Integer;
        name  : String(111);
        
    }
}