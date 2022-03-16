package com.example.trydisslow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class editModule extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);
        Intent intent = getIntent();
        String editableModule = intent.getExtras().getString("editableModule");

         String title;
        String code;
        String leader;
        String notes;

      // String editableModule = "module a";
        Toast.makeText(this,editableModule,
                Toast.LENGTH_SHORT).show();
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        String retrieveModule = "SELECT * from NEWMODULE8 WHERE title = '" + editableModule + "'";
//       // myBase.execSQL(retrieveModule);
        Cursor query = myBase.rawQuery(retrieveModule, null);
//
        if(query.moveToFirst()) {
            title = query.getString(0);
            code = query.getString(1);
            leader = query.getString(2);
            notes= query.getString(3);
//
            EditText moduleTitle = (EditText) findViewById(R.id.addModuleTitleBox);
        EditText moduleCod = (EditText) findViewById(R.id.addModuleCodeBox);
        EditText moduleLeader = (EditText) findViewById(R.id.addModuleLeader);
        EditText moduleNotes = (EditText) findViewById(R.id.addModuleNotesBox);
        moduleNotes.setText(notes);
       moduleCod.setText(code);
            moduleTitle.setText(title);

        moduleLeader.setText(leader);
}
//        Module m = new Module();
//        Button saveModuleButton = (Button) findViewById(R.id.buttonSaveModule);
//        saveModuleButton.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                String deleteStatement = "DELETE FROM NEWMODULE8 WHERE title = '" + editableModule + "'";
//                myBase.execSQL(deleteStatement);
//
//                m.nameMod = moduleTitle.getText().toString();
//
//                m.moduleCode = moduleCod.getText().toString();
//
//                m.courseLeader = moduleLeader.getText().toString();
//
//
//                m.modNotes  = moduleNotes.getText().toString();
//
//                // m.colour = "yellow";
//                //DatabaseSingleton db = DatabaseSingleton.getInstance();
//
//                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
//
//              //  myBase.execSQL("CREATE TABLE if not exists TESTMODULE(title TEXT, code TEXT, leader TEXT, notes TEXT);");
//                String insertStatement = "INSERT INTO NEWMODULE8 VALUES('" + m.nameMod + "','" + m.moduleCode + "','" + m.modNotes + "','" + m.courseLeader + "');";
//                // String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','"+ m.moduleCode + "','"  + m.courseLeader + "','"  + m.modNotes + "')\"";
//                myBase.execSQL(insertStatement);
//                //db.addModule(m);
//                Toast.makeText(editModule.this, "Saved Module " + m.nameMod,
//                        Toast.LENGTH_LONG).show();
//                startActivity(new Intent(editModule.this, ModulesAndClasses.class));
//            }
//        });
//
//

    }

}