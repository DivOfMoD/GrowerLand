package com.divofmod.quizer;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.divofmod.quizer.DataBase.DBHelper;
import com.divofmod.quizer.DataBase.DBReader;
import com.divofmod.quizer.Interfaces.ScrollViewListener;
import com.divofmod.quizer.QuizHelper.Audio;
import com.divofmod.quizer.QuizHelper.AudioRecorder;
import com.divofmod.quizer.QuizHelper.PhotoCamera;
import com.divofmod.quizer.QuizHelper.StopWatch;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener, ScrollViewListener, LocationListener {

    SharedPreferences sharedPreferences;
    SQLiteDatabase sqLiteDatabase;

    ArrayList<String[]> tableQuestion;
    ArrayList<String[]> tableAnswer;
    ArrayList<String[]> tableSelectiveQuestion;
    ArrayList<String[]> tableSelectiveAnswer;
    ArrayList<String[]> mConfig;

    HashMap<String, String> mConfigMap;

    String mUserId;

    ArrayList<Map<String, String[]>> answerSequenceInsideQuestions;
    ArrayList<String> goneNumbers;
    int num = -1;
    ArrayList<Integer> mQuestionSequence;

    int number = 1;
    String question_id;

    ArrayList<String> tableSequence;
    ArrayList<TableQuestion> tableQuestions;

    AnswerAdapter adapter;
    SelectiveQuestionAdapter selectiveQuestionAdapter;
    SelectiveAnswerAdapter selectiveAnswerAdapter;
    ListView selectiveAnswersListView;
    ArrayList<SelectiveAnswer> selectiveAnswersList;
    ListView listView;

    String[] currentQuestion = new String[1];
    ArrayList<String[]> currentAnswers;

    FrameLayout frameLayout;
    String photoName;
    int photoNumber = -1;

    Audio mAudio;

    LocationManager locationManager;

    TextView mQuestionTitle;
    ImageView questionPicture;

    ArrayList<TableQuestion> mTableQuestions;

    ObservableScrollView scrollView1;
    ObservableScrollView scrollView2;
    static boolean interceptScroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableSequence = new ArrayList<>();
        answerSequenceInsideQuestions = new ArrayList<>();
        goneNumbers = new ArrayList<>();
        mQuestionSequence = new ArrayList<>();
        mQuestionSequence.add(number);

        sharedPreferences = getSharedPreferences("data",
                Context.MODE_PRIVATE);

        sqLiteDatabase = new DBHelper(QuestionsActivity.this,
                sharedPreferences.getString("name_file", ""),
                new File(getFilesDir().toString() + getString(R.string.separator_path) + sharedPreferences.getString("name_file", "").substring(0, sharedPreferences.getString("name_file", "").length() - 4)),
                getString(R.string.sql_file_name),
                getString(R.string.old_sql_file_name)).getWritableDatabase();

        tableQuestion = DBReader.read(sqLiteDatabase,
                "question",
                new String[]{"id", "number", "title", "polyanswer", "max_answers", "picture", "questionnaire_id", "next_question", "is_filter", "table_id", "next_after_selective_question"});

        tableAnswer = DBReader.read(sqLiteDatabase,
                "answer",
                new String[]{"id", "title", "picture", "question_id", "next_question", "is_open", "table_question_id"});

        tableSelectiveQuestion = DBReader.read(sqLiteDatabase,
                "selective_question",
                new String[]{"id", "num", "title", "question_id"});

        tableSelectiveAnswer = DBReader.read(sqLiteDatabase,
                "selective_answer",
                new String[]{"id", "num", "title", "title_search", "parent_num", "selective_question_id"});

        mConfig = DBReader.read(sqLiteDatabase,
                "config",
                new String[]{"title", "value"});

        mUserId = DBReader.read(sqLiteDatabase, "questionnaire", "id");

        sqLiteDatabase.close();

        mConfigMap = new HashMap<>();
        for (int i = 0; i < mConfig.size(); i++)
            mConfigMap.put(mConfig.get(i)[0], mConfig.get(i)[1]);

        if (mConfigMap.get("audio").equals("1"))
            mAudio = new Audio(mConfigMap.get("audio_record_questions").split(","),
                    mConfigMap.get("audio_record_limit_time"),
                    mConfigMap.get("audio_speex_sample_rate"));

        if (mConfigMap.get("photo_questionnaire").equals("1"))
            photoNumber = new Random().nextInt(Integer.parseInt(mConfigMap.get("count_questions_min"))) + 1;


        /*String rsa;
        System.out.println(sharedPreferences.getString("login_admin", ""));
        System.out.println(sharedPreferences.getString("url", ""));
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            rsa = RSA.decrypt(mConfigMap.get("server"), QuestionsActivity.this);
            editor.putString("login_admin", rsa.split("\\|")[0]);
            editor.putString("url", rsa.split("\\|")[1]);
            editor.apply();
            System.out.println(sharedPreferences.getString("login_admin", ""));
            System.out.println(sharedPreferences.getString("url", ""));
        } catch (Exception e) {
            rsa = sharedPreferences.getString("login_admin", "") + "|" + sharedPreferences.getString("url", "");
        }*/


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        tableQuestions = new ArrayList<>();
        ArrayList<String[]> tableAnswers;
        for (int i = 0; i < tableQuestion.size(); i++) {
            if (!tableQuestion.get(i)[9].equals("0")) {
                tableAnswers = new ArrayList<>();
                for (int j = 0; j < tableAnswer.size(); j++)
                    if (tableAnswer.get(j)[3].equals(tableQuestion.get(i)[0]))
                        tableAnswers.add(tableAnswer.get(j));

                tableQuestions.add(
                        new TableQuestion(
                                tableQuestion.get(i)[0],
                                tableQuestion.get(i)[1],
                                tableQuestion.get(i)[2],
                                tableQuestion.get(i)[9],
                                tableAnswers)
                );
            }
        }

        setContentView(R.layout.activity_questions);

        scrollView1 = (ObservableScrollView) findViewById(R.id.scrollview1);
        scrollView1.setScrollViewListener(this);
        scrollView2 = (ObservableScrollView) findViewById(R.id.scrollview2);
        scrollView2.setScrollViewListener(this);

        findViewById(R.id.activity_questions).setOnTouchListener(new OnSwipeTouchListener(QuestionsActivity.this) {
            public void onSwipeRight() {
                onClick(findViewById(R.id.question_previous_button));
            }

            public void onSwipeLeft() {
                onClick(findViewById(R.id.question_next_button));
            }

        });
        findViewById(R.id.question_next_button).setOnClickListener(this);
        findViewById(R.id.question_previous_button).setOnClickListener(this);

        frameLayout = (FrameLayout) findViewById(R.id.surfaceHolder);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnTouchListener(new OnSwipeTouchListener(QuestionsActivity.this) {
            public void onSwipeRight() {
                onClick(findViewById(R.id.question_previous_button));
            }

            public void onSwipeLeft() {
                onClick(findViewById(R.id.question_next_button));
            }

        });

        mQuestionTitle = (TextView) findViewById(R.id.question_title);
        questionPicture = (ImageView) findViewById(R.id.question_picture);


        num++;
        nextQuestion();

        if (mAudio != null)
            if (mAudio.getAudioRecordQuestions().containsKey(0))
                AudioRecorder.Start(this, sharedPreferences.getString("login_admin", "") + "_" +
                        sharedPreferences.getString("login", "") + "_" +
                        sharedPreferences.getString("user_project_id", "") + "_" +
                        0 + "_" +
                        new SimpleDateFormat("yy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date()), mAudio.getAudioRecordLimitTime(), mAudio.getAudioSampleRate());

        StopWatch.setGlobalStart();
    }

    private void setCurrentQuestion() {

        for (String[] temp : tableQuestion)
            if (temp[1].equals(Integer.toString(number))) {
                currentQuestion = temp;
                question_id = temp[0];
                return;
            }
    }

    private void setCurrentAnswers() {
        currentAnswers = new ArrayList<>();
        for (String[] temp : tableAnswer)
            if (temp[3].equals(question_id)) {
                currentAnswers.add(temp);
            }
    }

    private String showQuestion(String number) {
        ArrayList<String[]> infoForQuestion = new ArrayList<>();
        String finalQ = "";
        String id = "";
        for (String[] tempQ : tableQuestion)
            if (tempQ[1].equals(number))
                id = tempQ[0];

        for (String[] temp : tableAnswer)
            if (temp[3].equals(id))
                infoForQuestion.add(temp);

        for (int i = 0; i < infoForQuestion.size(); i++)
            for (int j = 0; j < answerSequenceInsideQuestions.size(); j++)
                if (answerSequenceInsideQuestions.get(j).containsKey(infoForQuestion.get(i)[0])) {
                    if (answerSequenceInsideQuestions.get(j).get(infoForQuestion.get(i)[0])[0].equals(infoForQuestion.get(i)[0]))
                        finalQ += " " + infoForQuestion.get(i)[1] + ",";
                    break;
                }

        return finalQ.substring(1, finalQ.length() - 1);
    }

    private String showAnswer(String number, String numbers) {
        ArrayList<String[]> infoForAnswer = new ArrayList<>();
        String finalAnswer = "";
        String id = "";
        for (String[] tempQ : tableQuestion)
            if (tempQ[1].equals(number))
                id = tempQ[0];

        for (String[] temp : tableAnswer)
            if (temp[3].equals(id))
                infoForAnswer.add(temp);


        for (int i = 0; i < infoForAnswer.size(); i++)
            if (Integer.parseInt(numbers) == i + 1)
                finalAnswer = infoForAnswer.get(i)[1];
        return finalAnswer;
    }

    public void searchItem(String textToSearch) {
        for (int i = 0; i < selectiveAnswersList.size(); i++) {
            if (!selectiveAnswersList.get(i).getTitle().toLowerCase().contains(textToSearch.toLowerCase())) {
                selectiveAnswersList.remove(selectiveAnswersList.get(i));
                i--;
            }
        }
        selectiveAnswerAdapter.notifyDataSetChanged();
    }

    public void initList(int position) {
        selectiveAnswersList = new ArrayList<>();
        for (int i = 0; i < getModelSelectiveQuestion(position).getSelectiveAnswers().size(); i++)
            if (getModelSelectiveQuestion(position).getSelectiveAnswers().get(i).getVisibility())
                selectiveAnswersList.add(getModelSelectiveQuestion(position).getSelectiveAnswers().get(i));

        selectiveAnswerAdapter = new SelectiveAnswerAdapter(QuestionsActivity.this, selectiveAnswersList);
        selectiveAnswersListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        selectiveAnswersListView.setAdapter(selectiveAnswerAdapter);
    }

    private void nextQuestion() {
        startAudio();
        createPhoto();
        setCurrentQuestion();
        String sForRe;
        if (currentQuestion[2].contains("#")) {
            sForRe = currentQuestion[2].substring(currentQuestion[2].indexOf('#'), currentQuestion[2].lastIndexOf('#') + 1);
            String question = currentQuestion[2].substring(currentQuestion[2].indexOf('#') + 1, currentQuestion[2].indexOf('|'));
            mQuestionTitle.setText(currentQuestion[1] + ". " + currentQuestion[2].replace(sForRe, showQuestion(question)));
        } else
            mQuestionTitle.setText(currentQuestion[1] + ". " + currentQuestion[2]);

        if (!currentQuestion[5].equals("")) {
            questionPicture.setVisibility(View.VISIBLE);
            questionPicture.setImageURI(Uri.fromFile(new File(getFilesDir().toString() + getString(R.string.separator_path) + sharedPreferences.getString("name_file", "").substring(0, sharedPreferences.getString("name_file", "").length() - 4) + getString(R.string.separator_path) + "answerimages", currentQuestion[5])));
        } else
            questionPicture.setVisibility(View.GONE);

        if (currentQuestion[10].equals("0")) {
            if (!currentQuestion[9].equals("0")) {
                final ArrayList<TableQuestion> tempTableQuestions = new ArrayList<>();
                mTableQuestions = tempTableQuestions;

                for (int i = 0; i < tableQuestions.size(); i++)
                    for (int j = 0; j < tableSequence.size(); j++)
                        if (tableQuestions.get(i).getTableId() == Integer.parseInt(currentQuestion[9]))
                            if (tableSequence.get(j).equals(Integer.toString(tableQuestions.get(i).getNumber())))
                                tempTableQuestions.add(tableQuestions.get(i));

                if (tempTableQuestions.size() == 0)
                    for (int i = 0; i < tableQuestions.size(); i++)
                        if (tableQuestions.get(i).getTableId() == Integer.parseInt(currentQuestion[9]))
                            tempTableQuestions.add(tableQuestions.get(i));

                listView.setVisibility(View.GONE);
                mQuestionTitle.setVisibility(View.GONE);
                questionPicture.setVisibility(View.GONE);
                findViewById(R.id.question_underline).setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                findViewById(R.id.question_linear_with_table).setVisibility(View.VISIBLE);

                TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TableRow row = new TableRow(this);

                final TextView tableTitle = (TextView) findViewById(R.id.question_table_title);
                tableTitle.setText(TableHelper.doShifts(tempTableQuestions.get(0).getTitle().contains("*")
                        ? tempTableQuestions.get(0).getTitle().split("\\*")[0] : "", this));

                //header (fixed vertically)
                final TableLayout header = (TableLayout) findViewById(R.id.table_header);
                header.removeAllViews();
                header.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryTextMaterialLight));
                row.setLayoutParams(wrapWrapTableRowParams);
                for (int i = 0; i < tempTableQuestions.get(0).getTableAnswers().size(); i++) {
                    FrameLayout frameLayout = new FrameLayout(this);
                    frameLayout.setPadding(1, 0, 1, 1);
                    TextView textView = TableHelper.makeTableRowWithText(tempTableQuestions.get(0).getTableAnswers().get(i).getTitle(), this);
                    textView.setId(i);
                    textView.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundMaterialLight));
                    tempTableQuestions.get(0).getTableAnswers().get(i).setTextViewId(textView.getId());

                    frameLayout.addView(textView);
                    row.addView(frameLayout);
                }
                header.addView(row);
                //header (fixed horizontally)
                final TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
                fixedColumn.removeAllViews();
                fixedColumn.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryTextMaterialLight));

                //rest of the table (within a scroll view)
                final TableLayout scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);
                scrollablePart.removeAllViews();
                scrollablePart.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryTextMaterialLight));

                //Fixed part
                row = new TableRow(this);
                row.setLayoutParams(wrapWrapTableRowParams);

                for (int i = 0; i < tempTableQuestions.size(); i++) {
                    FrameLayout fixedFrameLayout = new FrameLayout(this);
                    fixedFrameLayout.setPadding(0, 1, 1, 1);
                    TextView fixedView = TableHelper.makeTableRowWithText(tempTableQuestions.get(i).getTitle().contains("*") ? tempTableQuestions.get(i).getTitle().split("\\*")[1] : tempTableQuestions.get(i).getTitle(), this);
                    fixedView.setId(i);
                    fixedView.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundMaterialLight));
                    tempTableQuestions.get(i).setTextViewId(fixedView.getId());

                    fixedFrameLayout.addView(fixedView);
                    fixedColumn.addView(fixedFrameLayout);
                    //Scrollable part
                    row = new TableRow(this);
                    row.setLayoutParams(wrapWrapTableRowParams);

                    for (int j = 0; j < tempTableQuestions.get(i).getTableAnswers().size(); j++) {
                        FrameLayout frameLayout = new FrameLayout(this);
                        frameLayout.setPadding(1, 1, 1, 1);
                        RelativeLayout relativeLayout = new RelativeLayout(this);
                        relativeLayout.setId(tempTableQuestions.get(i).getTableAnswers().get(j).getId() * 100);
                        relativeLayout.setGravity(Gravity.CENTER);
                        relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundMaterialLight));
                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setId(tempTableQuestions.get(i).getTableAnswers().get(j).getId());
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answerSequenceInsideQuestions.size() > 0)
                                    for (int i = answerSequenceInsideQuestions.size() - 1; i >= num; i--) {
                                        answerSequenceInsideQuestions.remove(i);
                                        goneNumbers.remove(i);
                                    }
                                RadioButton clickedRadioButton = (RadioButton) v;
                                if (clickedRadioButton.isChecked())
                                    for (int i = 0; i < tempTableQuestions.size(); i++)
                                        for (int j = 0; j < tempTableQuestions.get(i).getTableAnswers().size(); j++)
                                            if (clickedRadioButton.getId() == tempTableQuestions.get(i).getTableAnswers().get(j).getId()) {
                                                tempTableQuestions.get(i).getTableAnswers().get(j).setChecked(true);
                                                for (int a = 0; a < tempTableQuestions.get(i).getTableAnswers().size(); a++)
                                                    if (clickedRadioButton.getId() != tempTableQuestions.get(i).getTableAnswers().get(a).getId()) {
                                                        tempTableQuestions.get(i).getTableAnswers().get(a).setChecked(false);
                                                        RadioButton radioButton = (RadioButton) scrollablePart.findViewById(tempTableQuestions.get(i).getTableAnswers().get(a).getId());
                                                        radioButton.setChecked(false);
                                                    }
                                            }
                            }
                        });
                        relativeLayout.addView(radioButton);
                        frameLayout.addView(relativeLayout);
                        row.addView(frameLayout);
                    }
                    scrollablePart.addView(row);
                }
                final int[] headerWidth = new int[tempTableQuestions.get(0).getTableAnswers().size()];
                final int[] headerHeight = new int[tempTableQuestions.get(0).getTableAnswers().size()];
                final int[] columnHeight = new int[tempTableQuestions.size()];
                final int[] columnWidth = new int[tempTableQuestions.size()];

                new Thread(new Runnable() {
                    public void run() {

                        for (int i = 0; i < headerWidth.length; i++) {
                            headerWidth[i] = header.findViewById(tempTableQuestions.get(0).getTableAnswers().get(i).getTextViewId()).getMeasuredWidth();
                            headerHeight[i] = header.findViewById(tempTableQuestions.get(0).getTableAnswers().get(i).getTextViewId()).getMeasuredHeight();

                            if (headerWidth[i] == 0 || headerHeight[i] == 0)
                                i--;
                        }
                        for (int i = 0; i < columnHeight.length; i++) {
                            columnHeight[i] = fixedColumn.findViewById(tempTableQuestions.get(i).getTextViewId()).getMeasuredHeight();
                            columnWidth[i] = fixedColumn.findViewById(tempTableQuestions.get(i).getTextViewId()).getMeasuredWidth();

                            if (columnHeight[i] == 0 || columnWidth[i] == 0)
                                i--;
                        }

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              for (int i = 0; i < headerHeight.length; i++) {
                                                  header.findViewById(tempTableQuestions.get(0).getTableAnswers().get(i).getTextViewId()).setLayoutParams(new FrameLayout.LayoutParams(headerWidth[i] >= scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredWidth() ?
                                                          headerWidth[i] : scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredWidth(), TableHelper.getMaxValue(headerHeight)));
                                              }

                                              for (int j = 0; j < headerWidth.length; j++) {
                                                  scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(j).getId() * 100).setLayoutParams(new FrameLayout.LayoutParams(headerWidth[j] >= scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredWidth() ?
                                                          headerWidth[j] : scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredWidth(), TableHelper.getMaxValue(columnHeight) >= scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredHeight() ?
                                                          TableHelper.getMaxValue(columnHeight) : scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredHeight()));
                                              }

                                              for (int i = 0; i < columnHeight.length; i++) {
                                                  fixedColumn.findViewById(tempTableQuestions.get(i).getTextViewId()).setLayoutParams(new FrameLayout.
                                                          LayoutParams(tableTitle.getMeasuredWidth() > TableHelper.getMaxValue(columnWidth)
                                                          ? tableTitle.getMeasuredWidth() : TableHelper.getMaxValue(columnWidth)
                                                          , TableHelper.getMaxValue(columnHeight) >= scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredHeight() ?
                                                          TableHelper.getMaxValue(columnHeight) : scrollablePart.findViewById(tempTableQuestions.get(0).getTableAnswers().get(0).getId()).getMeasuredHeight()));
                                              }

                                              tableTitle.findViewById(R.id.question_table_title).setLayoutParams(new FrameLayout.LayoutParams(
                                                      tableTitle.getMeasuredWidth() > TableHelper.getMaxValue(columnWidth)
                                                              ? tableTitle.getMeasuredWidth() : TableHelper.getMaxValue(columnWidth)
                                                      , TableHelper.getMaxValue(headerHeight)));
                                          }
                                      }
                        );

                    }
                }).start();

                StopWatch.setStart();

            } else {
                setCurrentAnswers();
                ArrayList<Answer> answers = new ArrayList<>();
                String answer = "";
                String answerNum = "";
                for (int i = 0; i < currentAnswers.size(); i++) {
                    if (currentAnswers.get(i)[1].contains("#")) {
                        answer = currentAnswers.get(i)[1].substring(currentAnswers.get(i)[1].indexOf('#') + 1, currentAnswers.get(i)[1].indexOf('|'));
                        answerNum = currentAnswers.get(i)[1].substring(currentAnswers.get(i)[1].indexOf('|') + 1, currentAnswers.get(i)[1].lastIndexOf('#'));
                    }
                    answers.add(new Answer(
                            currentAnswers.get(i)[0],
                            currentAnswers.get(i)[1].contains("#") ? showAnswer(answer, answerNum) :
                                    currentAnswers.get(i)[1],
                            currentAnswers.get(i)[2].equals("") ? null : new File(getFilesDir().toString() + getString(R.string.separator_path) + sharedPreferences.getString("name_file", "").substring(0, sharedPreferences.getString("name_file", "").length() - 4) + getString(R.string.separator_path) + "answerimages", currentAnswers.get(i)[2]),
                            currentQuestion[3],
                            currentQuestion[4],
                            currentAnswers.get(i)[5],
                            currentAnswers.get(i)[4],
                            currentAnswers.get(i)[6]
                    ));
                }
                Collections.sort(answers, new Comparator<Answer>() {
                    public int compare(Answer o1, Answer o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });
                adapter = new AnswerAdapter(QuestionsActivity.this, answers);
                findViewById(R.id.question_linear_with_table).setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mQuestionTitle.setVisibility(View.VISIBLE);
                findViewById(R.id.question_underline).setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(adapter);
                StopWatch.setStart();
            }
        } else {
            ArrayList<String[]> currentSelectiveAnswers;

            ArrayList<SelectiveQuestion> selectiveQuestions = new ArrayList<>();
            for (int i = 0; i < tableSelectiveQuestion.size(); i++) {
                currentSelectiveAnswers = new ArrayList<>();
                for (int j = 0; j < tableSelectiveAnswer.size(); j++)
                    if (tableSelectiveAnswer.get(j)[5].equals(tableSelectiveQuestion.get(i)[0]))
                        currentSelectiveAnswers.add(tableSelectiveAnswer.get(j));

                if (tableSelectiveQuestion.get(i)[3].equals(currentQuestion[0]))
                    selectiveQuestions.add(new SelectiveQuestion(
                            tableSelectiveQuestion.get(i)[0],
                            tableSelectiveQuestion.get(i)[2],
                            currentSelectiveAnswers,
                            currentQuestion[10]));
            }

            Collections.sort(selectiveQuestions, new Comparator<SelectiveQuestion>() {
                public int compare(SelectiveQuestion o1, SelectiveQuestion o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
            selectiveQuestionAdapter = new SelectiveQuestionAdapter(QuestionsActivity.this, selectiveQuestions);
            findViewById(R.id.question_linear_with_table).setVisibility(View.GONE);
            mQuestionTitle.setVisibility(View.VISIBLE);
            findViewById(R.id.question_underline).setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(selectiveQuestionAdapter);
            StopWatch.setStart();
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View itemClicked, final int position,
                                                long id) {
                            final SelectiveQuestion selectiveQuestion = getModelSelectiveQuestion(position);

                            AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);

                            View view = getLayoutInflater().inflate(R.layout.selective_answers_dialog, null);

                            selectiveAnswersListView = (ListView) view.findViewById(R.id.selective_answers);
                            initList(position);

                            EditText selectiveAnswersSearch = (EditText) view.findViewById(R.id.selective_answers_search);

                            selectiveAnswersSearch.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if (s.toString().equals("")) {
                                        // reset listview
                                        initList(position);
                                    } else {
                                        // perform search
                                        initList(position);
                                        searchItem(s.toString());
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }

                            });

                            builder.setTitle(selectiveQuestion.getTitle())
                                    .setIcon(R.drawable.list)
                                    .setCancelable(false)
                                    .setView(view)
                                    .setPositiveButton("Ок",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    for (int i = 0; i < listView.getAdapter().getCount() - 1; i++)
                                                        for (int j = 0; j < getModelSelectiveQuestion(i).getSelectiveAnswers().size(); j++)
                                                            for (int b = 0; b < getModelSelectiveQuestion(i + 1).getSelectiveAnswers().size(); b++)
                                                                if (getModelSelectiveQuestion(i).getSelectiveAnswers().get(j).getNum() == getModelSelectiveQuestion(i + 1).getSelectiveAnswers().get(b).getParentNum()) {
                                                                    getModelSelectiveQuestion(i + 1).getSelectiveAnswers().get(b).setVisibility(getModelSelectiveQuestion(i).getSelectiveAnswers().get(j).getCheck());
                                                                    if (!getModelSelectiveQuestion(i + 1).getSelectiveAnswers().get(b).getVisibility())
                                                                        getModelSelectiveQuestion(i + 1).getSelectiveAnswers().get(b).setCheck(false);
                                                                }
                                                    dialog.cancel();
                                                    selectiveQuestionAdapter.notifyDataSetChanged();
                                                }
                                            }
                                    );
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
            );
        }

    }

    private Answer getModelAnswer(int position) {
        return ((adapter).getItem(position));
    }

    private SelectiveQuestion getModelSelectiveQuestion(int position) {
        return ((selectiveQuestionAdapter).getItem(position));
    }

    private SelectiveAnswer getModelSelectiveAnswer(int position) {
        return ((selectiveAnswerAdapter).getItem(position));
    }

    //Обрабатывваем нажатие НАЗАД/ДАЛЕЕ
    @Override
    public void onClick(View v) {
        Answer answer;
        SelectiveQuestion selectiveQuestion;
        switch (v.getId()) {
            case R.id.question_next_button:
                int count;

                if (currentQuestion[10].equals("0")) {
                    if (!currentQuestion[9].equals("0")) {
                        count = 0;
                        int countBeforeAnswerCheck;
                        for (int i = 0; i < mTableQuestions.size(); i++) {
                            countBeforeAnswerCheck = count;
                            for (int j = 0; j < mTableQuestions.get(i).getTableAnswers().size(); j++)
                                if (mTableQuestions.get(i).getTableAnswers().get(j).isChecked())
                                    count++;
                            if (count == countBeforeAnswerCheck)
                                break;
                        }
                        if (count != mTableQuestions.size())
                            return;

                        //0 - answer_id
                        //1 - duration_time_question
                        //2 - text_open_answer
                        Map<String, String[]> userAnswers = new HashMap<>();

                        for (int i = 0; i < mTableQuestions.size(); i++)
                            for (int j = 0; j < mTableQuestions.get(i).getTableAnswers().size(); j++)
                                if (mTableQuestions.get(i).getTableAnswers().get(j).isChecked())
                                    userAnswers.put(Integer.toString(mTableQuestions.get(i).getTableAnswers().get(j).getId()), new String[]{
                                            Integer.toString(mTableQuestions.get(i).getTableAnswers().get(j).getId()),
                                            StopWatch.getTime(),
                                            ""});

                        saveAudio();

                        for (int i = 0; i < mTableQuestions.size(); i++)
                            for (int j = 0; j < mTableQuestions.get(i).getTableAnswers().size(); j++)
                                if (!mTableQuestions.get(i).getTableAnswers().get(j).isChecked())
                                    mQuestionSequence.removeAll(Arrays.asList(mTableQuestions.get(i).getTableAnswers().get(j).getNextQuestion()));

                        for (int i = 0; i < mTableQuestions.size(); i++)
                            for (int j = 0; j < mTableQuestions.get(i).getTableAnswers().size(); j++)
                                if (mTableQuestions.get(i).getTableAnswers().get(j).isChecked())
                                    if (!mQuestionSequence.contains(mTableQuestions.get(i).getTableAnswers().get(j).getNextQuestion()))
                                        mQuestionSequence.add(mTableQuestions.get(i).getTableAnswers().get(j).getNextQuestion());

                        Collections.sort(mQuestionSequence);

                        for (int i = 0; i < mQuestionSequence.size(); i++)
                            if (mQuestionSequence.get(i).equals(number)) {
                                number = i + 1 == mQuestionSequence.size() ? mTableQuestions.get(0).getTableAnswers().get(0).getNextQuestion() : mQuestionSequence.get(i + 1);
                                break;
                            }

                        answerSequenceInsideQuestions.add(userAnswers);
                        goneNumbers.add(currentQuestion[1]);

                        //Если вопрос последний, переходим в следующую активность.
                        addToDB(number);

                    } else {
                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            answer = getModelAnswer(i);
                            if (answer.getCheck())
                                if (answer.getIsOpenAnswer())
                                    if (answer.getOpenAnswer().isEmpty())
                                        answer.setOpenAnswerError("Введите текст!");
                        }
                        adapter.notifyDataSetChanged();

                        count = 0;
                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            answer = getModelAnswer(i);
                            if (answer.getCheck()) {
                                if (answer.getIsOpenAnswer()) {
                                    if (answer.getOpenAnswer().isEmpty())
                                        return;
                                    else
                                        count++;
                                } else
                                    count++;
                            }
                        }

                        if (count == 0)
                            return;

                        //0 - answer_id
                        //1 - duration_time_question
                        //2 - text_open_answer
                        Map<String, String[]> userAnswers = new HashMap<>();

                        for (int i = listView.getAdapter().getCount() - 1; i >= 0; i--) {
                            answer = getModelAnswer(i);
                            if (answer.getCheck()) {
                                View viewAnswer = listView.getChildAt(i);
                                EditText answerOpen = null;
                                if (answer.getIsOpenAnswer())
                                    answerOpen = (EditText) viewAnswer.findViewById(R.id.answer_open);
                                userAnswers.put(answer.getId(), new String[]{
                                        answer.getId(),
                                        StopWatch.getTime(),
                                        answerOpen != null ? answerOpen.getText().toString() : ""});
                                if (currentQuestion[8].equals("1"))
                                    for (int j = 0; j < answer.getTableQuestionId().split(",").length; j++)
                                        if (!tableSequence.contains(answer.getTableQuestionId().split(",")[j]))
                                            tableSequence.add(answer.getTableQuestionId().split(",")[j]);
                            } else if (currentQuestion[8].equals("1"))
                                for (int j = 0; j < answer.getTableQuestionId().split(",").length; j++)
                                    if (tableSequence.contains(answer.getTableQuestionId().split(",")[j]))
                                        tableSequence.remove(answer.getTableQuestionId().split(",")[j]);
                        }

                        saveAudio();

                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            answer = getModelAnswer(i);
                            if (!answer.getCheck())
                                mQuestionSequence.removeAll(Arrays.asList(answer.getNextQuestion()));
                        }

                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            answer = getModelAnswer(i);
                            if (answer.getCheck())
                                if (!mQuestionSequence.contains(answer.getNextQuestion()))
                                    if (currentQuestion[8].equals("1")) {
                                        mQuestionSequence.add(answer.getNextQuestion());
                                        break;
                                    } else
                                        mQuestionSequence.add(answer.getNextQuestion());
                        }

                        Collections.sort(mQuestionSequence);

                        for (int i = 0; i < mQuestionSequence.size(); i++)
                            if (mQuestionSequence.get(i).equals(number)) {
                                number = i + 1 == mQuestionSequence.size() ? getModelAnswer(0).getNextQuestion() : mQuestionSequence.get(i + 1);
                                break;
                            }

                        answerSequenceInsideQuestions.add(userAnswers);
                        goneNumbers.add(currentQuestion[1]);

                        //Если вопрос последний, переходим в следующую активность.
                        addToDB(number);
                    }
                } else {
                    count = 0;
                    for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                        selectiveQuestion = getModelSelectiveQuestion(i);
                        for (int j = 0; j < selectiveQuestion.getSelectiveAnswers().size(); j++)
                            if (selectiveQuestion.getSelectiveAnswers().get(j).getCheck()) {
                                count++;
                                break;
                            }
                    }

                    if (count != listView.getAdapter().getCount())
                        return;
                    //0 - answer_id
                    //1 - duration_time_question
                    Map<String, String[]> userAnswers = new HashMap<>();
                    for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                        selectiveQuestion = getModelSelectiveQuestion(i);
                        for (int j = 0; j < selectiveQuestion.getSelectiveAnswers().size(); j++)
                            if (selectiveQuestion.getSelectiveAnswers().get(j).getCheck()) {
                                userAnswers.put(Integer.toString(selectiveQuestion.getSelectiveAnswers().get(j).getId()), new String[]{
                                        Integer.toString(selectiveQuestion.getSelectiveAnswers().get(j).getId()),
                                        StopWatch.getTime()});
                            }
                    }

                    saveAudio();

                    mQuestionSequence.add(getModelSelectiveQuestion(0).getNextQuestion());

                    Collections.sort(mQuestionSequence);

                    for (int i = 0; i < mQuestionSequence.size(); i++)
                        if (mQuestionSequence.get(i).equals(number)) {
                            number = i + 1 == mQuestionSequence.size() ? getModelSelectiveQuestion(0).getNextQuestion() : mQuestionSequence.get(i + 1);
                            break;
                        }

                    answerSequenceInsideQuestions.add(userAnswers);
                    goneNumbers.add(currentQuestion[1]);

                    //Если вопрос последний, переходим в следующую активность.
                    addToDB(number);
                }

                num++;
                //Визуализация
                nextQuestion();
                if (currentQuestion[10].equals("0")) {
                    if (!currentQuestion[9].equals("0")) {
                        for (int i = 0; i < mTableQuestions.size(); i++)
                            for (int j = 0; j < mTableQuestions.get(i).getTableAnswers().size(); j++)
                                for (int a = 0; a < answerSequenceInsideQuestions.size(); a++)
                                    if (answerSequenceInsideQuestions.get(a).containsKey(Integer.toString(mTableQuestions.get(i).getTableAnswers().get(j).getId()))) {
                                        mTableQuestions.get(i).getTableAnswers().get(j).setChecked(true);
                                        RadioButton radioButton = (RadioButton) findViewById(mTableQuestions.get(i).getTableAnswers().get(j).getId());
                                        radioButton.setChecked(true);
                                    }
                    } else
                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            answer = getModelAnswer(i);
                            for (int j = 0; j < answerSequenceInsideQuestions.size(); j++)
                                if (answerSequenceInsideQuestions.get(j).containsKey(answer.getId())) {
                                    answer.setCheck(true);
                                    answer.setOpenAnswer(answerSequenceInsideQuestions.get(j).get(answer.getId())[2]);
                                }
                        }
                } else
                    for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                        selectiveQuestion = getModelSelectiveQuestion(i);
                        for (int j = 0; j < selectiveQuestion.getSelectiveAnswers().size(); j++)
                            for (int k = 0; k < answerSequenceInsideQuestions.size(); k++)
                                if (answerSequenceInsideQuestions.get(k).containsKey(Integer.toString(selectiveQuestion.getSelectiveAnswers().get(j).getId())))
                                    selectiveQuestion.getSelectiveAnswers().get(j).setCheck(true);
                    }
                break;
            case R.id.question_previous_button:
                if (number == 1)
                    return;

                saveAudio();

                num--;
                number = Integer.parseInt(goneNumbers.get(num));
                //Визуализация
                nextQuestion();

                if (currentQuestion[10].equals("0")) {
                    if (!currentQuestion[9].equals("0"))
                        for (int i = 0; i < mTableQuestions.size(); i++)
                            for (int j = 0; j < mTableQuestions.get(i).getTableAnswers().size(); j++)
                                mQuestionSequence.removeAll(Arrays.asList(mTableQuestions.get(i).getTableAnswers().get(j).getNextQuestion()));
                    else
                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            answer = getModelAnswer(i);
                            mQuestionSequence.removeAll(Arrays.asList(answer.getNextQuestion()));
                        }
                } else
                    for (int i = 0; i < listView.getAdapter().getCount(); i++)
                        mQuestionSequence.removeAll(Arrays.asList(getModelSelectiveQuestion(0).getNextQuestion()));

                if (currentQuestion[10].equals("0")) {
                    if (!currentQuestion[9].equals("0")) {
                        for (int i = 0; i < mTableQuestions.size(); i++)
                            for (int j = 0; j < mTableQuestions.get(i).getTableAnswers().size(); j++)
                                for (int a = 0; a < answerSequenceInsideQuestions.size(); a++)
                                    if (answerSequenceInsideQuestions.get(a).containsKey(Integer.toString(mTableQuestions.get(i).getTableAnswers().get(j).getId()))) {
                                        mTableQuestions.get(i).getTableAnswers().get(j).setChecked(true);
                                        RadioButton radioButton = (RadioButton) findViewById(mTableQuestions.get(i).getTableAnswers().get(j).getId());
                                        radioButton.setChecked(true);
                                    }
                    } else
                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            answer = getModelAnswer(i);
                            for (int j = 0; j < answerSequenceInsideQuestions.size(); j++)
                                if (answerSequenceInsideQuestions.get(j).containsKey(answer.getId())) {
                                    answer.setCheck(true);
                                    answer.setOpenAnswer(answerSequenceInsideQuestions.get(j).get(answer.getId())[2]);
                                }
                        }
                } else
                    for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                        selectiveQuestion = getModelSelectiveQuestion(i);
                        for (int j = 0; j < selectiveQuestion.getSelectiveAnswers().size(); j++)
                            for (int k = 0; k < answerSequenceInsideQuestions.size(); k++)
                                if (answerSequenceInsideQuestions.get(k).containsKey(Integer.toString(selectiveQuestion.getSelectiveAnswers().get(j).getId())))
                                    selectiveQuestion.getSelectiveAnswers().get(j).setCheck(true);
                    }
                break;
        }
    }

    private void addToDB(int model) {
        if (model == 0) {
            sqLiteDatabase = new DBHelper(QuestionsActivity.this,
                    sharedPreferences.getString("name_file", ""),
                    new File(getFilesDir().toString() + getString(R.string.separator_path) + sharedPreferences.getString("name_file", "").substring(0, sharedPreferences.getString("name_file", "").length() - 4)),
                    getString(R.string.sql_file_name),
                    getString(R.string.old_sql_file_name)).getWritableDatabase();
            if (mAudio != null) {
                if (mAudio.getAudioRecordQuestions().containsKey(0)) {
                    String s = AudioRecorder.Stop();
                    sqLiteDatabase.execSQL("create table if not exists " + "audioFiles" + "(names text);");
                    sqLiteDatabase.execSQL("insert into " + "audioFiles" + "(names)" +
                            "values('" + s + (mQuestionSequence.size() - 1) + "')");
                    new File(Environment.getExternalStorageDirectory() + "/" + s + ".amr").renameTo(new File(Environment.getExternalStorageDirectory() + "/" + s + "_" + (mQuestionSequence.size() - 1) + ".amr"));

                } else {
                    for (int i = 0; i < mAudio.getAudioRecordQuestions().size(); i++) {
                        sqLiteDatabase.execSQL("create table if not exists " + "audioFiles" + "(names text);");
                        sqLiteDatabase.execSQL("insert into " + "audioFiles" + "(names)" +
                                "values('" + mAudio.getAudioRecordQuestions().values().toArray()[i] + "')");
                    }
                }
            }

            if (photoNumber != -1 && photoName != null) {
                sqLiteDatabase.execSQL("create table if not exists " + "photoFiles" + "(names text);");
                sqLiteDatabase.execSQL("insert into " + "photoFiles" + "(names)" +
                        "values('" + photoName + "')");
            }

            String date = new SimpleDateFormat("dd_MM_yy_HH_mm", Locale.getDefault()).format(new Date());
            String answerSQL = "answers_" + date; //Имя анкеты
            String answerSQLSelective = "answers_selective_" + date; //Имя анкеты + selective
            String commnSQL = "common_" + date; //Общие данные

            //Запоминаем названия таблиц из БД
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (sharedPreferences.getString("Quezzes", "").equals(""))
                editor.putString("Quezzes", date);
            else
                editor.putString("Quezzes", sharedPreferences.getString("Quezzes", "") + ";" + date);

            editor.apply();

            Toast.makeText(this, sharedPreferences.getString("gps", ""), Toast.LENGTH_LONG).show();

            //Создаем таблицы в БД
            sqLiteDatabase.execSQL("create table if not exists " + answerSQL + "(answer_id text,duration_time_question text,text_open_answer text);");
            sqLiteDatabase.execSQL("create table if not exists " + answerSQLSelective + "(answer_id text,duration_time_question text);");
            sqLiteDatabase.execSQL("create table if not exists " + commnSQL + "(project_id text, questionnaire_id text, user_project_id text, date_interview text, gps text, duration_time_questionnaire text);");

            //Заполняем таблицы
            for (int i = 0; i < answerSequenceInsideQuestions.size(); i++)
                for (String[] value : answerSequenceInsideQuestions.get(i).values()) {
                    //Записываем в базу ответы
                    if (value.length == 3) {
                        sqLiteDatabase.execSQL("insert into " + answerSQL + " (answer_id,duration_time_question,text_open_answer) " +
                                "values('" + value[0] + "','" + value[1] + "','" + value[2] + "')");

                        System.out.println("ID=" + value[0] + " duration=" + value[1] + " text=" + value[2]);
                    } else {
                        sqLiteDatabase.execSQL("insert into " + answerSQLSelective + " (answer_id,duration_time_question) " +
                                "values('" + value[0] + "','" + value[1] + "')");

                        System.out.println("ID=" + value[0] + " duration=" + value[1]);
                    }
                }
            sqLiteDatabase.execSQL("insert into " + commnSQL + " (project_id,questionnaire_id,user_project_id,date_interview,gps,duration_time_questionnaire) " +
                    "values('" +
                    DBReader.read(sqLiteDatabase, "project", "id") + "','" +
                    DBReader.read(sqLiteDatabase, "questionnaire", "id") + "','" +
                    sharedPreferences.getString("usr_project_id", "") + "','" +
                    new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()) + "','" +
                    sharedPreferences.getString("gps", "") + "','" +
                    StopWatch.getGlobalTime() + "')");

            sqLiteDatabase.close();
            startActivity(new Intent(this, QuestionnaireActivity.class));
            finish();
        }
    }

    private class AnswerAdapter extends ArrayAdapter<Answer> {

        private LayoutInflater mInflater;

        AnswerAdapter(Context context, ArrayList<Answer> list) {
            super(context, R.layout.answer_item, list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final ViewHolder holder;
            View row = convertView;
            if (row == null) {
                row = mInflater.inflate(R.layout.answer_item, parent, false);
                holder = new ViewHolder();
                holder.answerTitle = (TextView) row.findViewById(R.id.answer_title);
                holder.answerRadio = (RadioButton) row.findViewById(R.id.answer_radio);
                holder.answerCheck = (CheckBox) row.findViewById(R.id.answer_check);
                holder.answerPicture = (ImageView) row.findViewById(R.id.answer_picture);
                holder.answerOpen = (EditText) row.findViewById(R.id.answer_open);
                row.setTag(holder);
            } else
                holder = (ViewHolder) row.getTag();

            final Answer answer = getModelAnswer(position);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answerSequenceInsideQuestions.size() > 0)
                        for (int i = answerSequenceInsideQuestions.size() - 1; i >= num; i--) {
                            answerSequenceInsideQuestions.remove(i);
                            goneNumbers.remove(i);
                        }
                    switch (v.getId()) {
                        case R.id.answer_check:
                            CheckBox selectedChB = (CheckBox) v;
                            if (answer.getMaxAnswers() == 0) {
                                answer.setCheck(selectedChB.isChecked());
                            } else {
                                int countTrue = 0;

                                View viewAnswer;
                                CheckBox answerCheck;

                                for (int i = 0; i < listView.getAdapter().getCount(); i++)
                                    if (getModelAnswer(i).getCheck())
                                        countTrue++;

                                if (countTrue == answer.getMaxAnswers()) {
                                    answer.setCheck(false);
                                    for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                                        viewAnswer = listView.getChildAt(i);
                                        answerCheck = (CheckBox) viewAnswer.findViewById(R.id.answer_check);
                                        answerCheck.setEnabled(true);
                                    }

                                } else {
                                    answer.setCheck(selectedChB.isChecked());

                                    if (countTrue == answer.getMaxAnswers() - 1)
                                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                                            viewAnswer = listView.getChildAt(i);
                                            answerCheck = (CheckBox) viewAnswer.findViewById(R.id.answer_check);
                                            if (!answerCheck.isChecked())
                                                answerCheck.setEnabled(false);
                                        }

                                    for (int i = 0; i < listView.getAdapter().getCount(); i++)
                                        if (getModelAnswer(i).getCheck())
                                            countTrue++;

                                    if (countTrue <= answer.getMaxAnswers())
                                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                                            viewAnswer = listView.getChildAt(i);
                                            answerCheck = (CheckBox) viewAnswer.findViewById(R.id.answer_check);
                                            if (!answerCheck.isChecked())
                                                answerCheck.setEnabled(true);
                                        }
                                }
                            }
                            notifyDataSetChanged();
                            break;
                        case R.id.answer_radio:
                            RadioButton selectedRB = (RadioButton) v;
                            if (selectedRB.isChecked()) {
                                answer.setCheck(true);
                                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                                    if (i != position)
                                        getModelAnswer(i).setCheck(false);
                                }
                                notifyDataSetChanged();
                            }
                            break;
                        case R.id.answer_title:
                            if (holder.answerCheck.isEnabled() && holder.answerRadio.isEnabled())
                                if (answer.getPolyAnswer()) {
                                    holder.answerCheck.setChecked(!holder.answerCheck.isChecked());
                                    onClick(holder.answerCheck);
                                } else {
                                    holder.answerRadio.setChecked(true);
                                    onClick(holder.answerRadio);
                                }
                            break;
                        case R.id.answer_picture:
                            if (holder.answerCheck.isEnabled() && holder.answerRadio.isEnabled())
                                if (answer.getPolyAnswer()) {
                                    holder.answerCheck.setChecked(!holder.answerCheck.isChecked());
                                    onClick(holder.answerCheck);
                                } else {
                                    holder.answerRadio.setChecked(true);
                                    onClick(holder.answerRadio);
                                }
                            break;
                        case R.id.answer_open:
                            if (holder.answerCheck.isEnabled() && holder.answerRadio.isEnabled()) {
                                if (answer.getPolyAnswer()) {
                                    holder.answerCheck.setChecked(true);
                                    onClick(holder.answerCheck);
                                } else {
                                    holder.answerRadio.setChecked(true);
                                    onClick(holder.answerRadio);
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);

                                View view = getLayoutInflater().inflate(R.layout.open_answer_dialog, null);

                                TextView answerTitle = (TextView) view.findViewById(R.id.answer_title);
                                answerTitle.setText(answer.getTitle());

                                final EditText answerOpen = (EditText) view.findViewById(R.id.answer_open);
                                answerOpen.setText(answer.getOpenAnswer());

                                builder.setIcon(R.drawable.edit)
                                        .setTitle(mQuestionTitle.getText())
                                        .setCancelable(false)
                                        .setView(view)

                                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                answer.setOpenAnswer(answerOpen.getText().toString());
                                                if (!answer.getOpenAnswer().isEmpty())
                                                    answer.setOpenAnswerError(null);
                                                notifyDataSetChanged();
                                            }
                                        })

                                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })

                                        .show();
                            }
                            break;

                    }
                }

            };

            holder.answerTitle.setOnClickListener(onClickListener);
            holder.answerPicture.setOnClickListener(onClickListener);
            holder.answerOpen.setOnClickListener(onClickListener);

            holder.answerTitle.setText(answer.getTitle());


            if (answer.getPolyAnswer()) {
                holder.answerCheck.setVisibility(View.VISIBLE);
                holder.answerCheck.setChecked(answer.getCheck());
                holder.answerCheck.setOnClickListener(onClickListener);

            } else {
                holder.answerRadio.setVisibility(View.VISIBLE);
                holder.answerRadio.setChecked(answer.getCheck());
                holder.answerRadio.setOnClickListener(onClickListener);
            }

            if (answer.getPicture() != null) {
                holder.answerPicture.setVisibility(View.VISIBLE);
                holder.answerPicture.setImageURI(Uri.fromFile(answer.getPicture()));
            }

            if (answer.getIsOpenAnswer()) {
                holder.answerOpen.setVisibility(View.VISIBLE);
                holder.answerOpen.setText(answer.getOpenAnswer());
                holder.answerOpen.setError(answer.getOpenAnswerError());
            }

            String quota = sharedPreferences.getString("quota", "1");
            String[] quotasTemp = quota.substring(1, quota.length() - 1).split(";");
            for (String re : quotasTemp) {
                if (re.equals("1"))
                    return row;
            }

            ArrayList<String[]> quotas = new ArrayList<>();
            for (String aQuotasTemp : quotasTemp)
                if (aQuotasTemp.split("\\|")[2].equals("0") || aQuotasTemp.split("\\|")[1].equals(aQuotasTemp.split("\\|")[2]))
                    quotas.add(aQuotasTemp.split("\\|")[0].split(","));

            for (int i = 0; i < quotas.size(); i++)
                if (quotas.get(i)[quotas.get(i).length - 1].equals(answer.getId())) {
                    for (int j = 0; j < quotas.get(i).length - 1; j++)
                        for (int k = 0; k < answerSequenceInsideQuestions.size(); k++)
                            if (!answerSequenceInsideQuestions.get(k).containsKey(quotas.get(i)[j])) {
                                if (k == answerSequenceInsideQuestions.size() - 1)
                                    break;
                            } else {
                                holder.answerCheck.setEnabled(false);
                                holder.answerRadio.setEnabled(false);
                            }
                }

            return row;
        }

        class ViewHolder {
            TextView answerTitle;
            RadioButton answerRadio;
            CheckBox answerCheck;
            ImageView answerPicture;
            EditText answerOpen;
        }

    }

    private class SelectiveQuestionAdapter extends ArrayAdapter<SelectiveQuestion> {

        private LayoutInflater mInflater;

        SelectiveQuestionAdapter(Context context, ArrayList<SelectiveQuestion> list) {
            super(context, R.layout.selective_question_item, list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final ViewHolder holder;
            View row = convertView;
            if (row == null) {
                row = mInflater.inflate(R.layout.selective_question_item, parent, false);
                holder = new ViewHolder();
                holder.selectiveQuestionTitle = (TextView) row.findViewById(R.id.selective_question_title);
                holder.selectiveAnswerTitles = (TextView) row.findViewById(R.id.selective_answer_titles);
                row.setTag(holder);
            } else
                holder = (ViewHolder) row.getTag();

            final SelectiveQuestion selectiveQuestion = getModelSelectiveQuestion(position);

            holder.selectiveQuestionTitle.setText(selectiveQuestion.getTitle());

            ArrayList<String> state = new ArrayList<>();

            for (int i = 0; i < selectiveQuestion.getSelectiveAnswers().size(); i++) {
                if (selectiveQuestion.getSelectiveAnswers().get(i).getCheck()) {
                    state.add(selectiveQuestion.getSelectiveAnswers().get(i).getTitle());
                }
            }

            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < state.size(); i++) {
                if (i != state.size() - 1)
                    temp.append(state.get(i) + "\n");
                else
                    temp.append(state.get(i));
            }

            holder.selectiveAnswerTitles.setText(temp.toString());

            if (holder.selectiveAnswerTitles.getText().equals(""))
                holder.selectiveAnswerTitles.setVisibility(View.GONE);
            else
                holder.selectiveAnswerTitles.setVisibility(View.VISIBLE);

            return row;
        }

        class ViewHolder {
            TextView selectiveQuestionTitle;
            TextView selectiveAnswerTitles;

        }

    }

    private class SelectiveAnswerAdapter extends ArrayAdapter<SelectiveAnswer> {

        private LayoutInflater mInflater;

        SelectiveAnswerAdapter(Context context, ArrayList<SelectiveAnswer> list) {
            super(context, R.layout.selective_answer_item, list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final ViewHolder holder;
            View row = convertView;
            if (row == null) {
                row = mInflater.inflate(R.layout.selective_answer_item, parent, false);
                holder = new ViewHolder();
                holder.getSelectiveAnswerTitle = (TextView) row.findViewById(R.id.selective_answer_item_title);
                holder.selectiveAnswerCheck = (CheckBox) row.findViewById(R.id.selective_answer_item_check);
                row.setTag(holder);
            } else
                holder = (ViewHolder) row.getTag();

            final SelectiveAnswer selectiveAnswer = getModelSelectiveAnswer(position);

            holder.getSelectiveAnswerTitle.setText(selectiveAnswer.getTitle());
            holder.getSelectiveAnswerTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answerSequenceInsideQuestions.size() > 0)
                        for (int i = answerSequenceInsideQuestions.size() - 1; i >= num; i--) {
                            answerSequenceInsideQuestions.remove(i);
                            goneNumbers.remove(i);
                        }
                    selectiveAnswer.setCheck(!selectiveAnswer.getCheck());
                    notifyDataSetChanged();
                }
            });
            holder.selectiveAnswerCheck.setChecked(selectiveAnswer.getCheck());
            holder.selectiveAnswerCheck.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (answerSequenceInsideQuestions.size() > 0)
                        for (int i = answerSequenceInsideQuestions.size() - 1; i >= num; i--) {
                            answerSequenceInsideQuestions.remove(i);
                            goneNumbers.remove(i);
                        }
                    CheckBox selectedChB = (CheckBox) v;
                    selectiveAnswer.setCheck(selectedChB.isChecked());
                    notifyDataSetChanged();

                }
            });
            return row;
        }

        class ViewHolder {
            TextView getSelectiveAnswerTitle;
            CheckBox selectiveAnswerCheck;
        }

    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                QuestionsActivity.this);
        quitDialog.setCancelable(true)
                .setIcon(R.drawable.ico)
                .setTitle("Выход из опроса")
                .setMessage("Выйти из опроса?")

                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(QuestionsActivity.this, ProjectActivity.class));
                        finish();
                    }
                })

                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })

                .show();
    }

    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (interceptScroll) {
            interceptScroll = false;
            if (scrollView == scrollView1) {
                scrollView2.onOverScrolled(x, y, true, true);
            } else if (scrollView == scrollView2) {
                scrollView1.onOverScrolled(x, y, true, true);
            }
            interceptScroll = true;
        }
    }

    private void saveAudio() {
        if (mAudio != null)
            if (mAudio.getAudioRecordQuestions().containsKey(number))
                mAudio.getAudioRecordQuestions().put(number, AudioRecorder.Stop());
    }

    private void startAudio() {
        if (mAudio != null)
            if (mAudio.getAudioRecordQuestions().containsKey(number) && !mAudio.getAudioRecordQuestions().containsKey(0)) {
                if (mAudio.getAudioRecordQuestions().get(number) == null)
                    AudioRecorder.Start(this, sharedPreferences.getString("login_admin", "") + "_" +
                            sharedPreferences.getString("login", "") + "_" +
                            sharedPreferences.getString("user_project_id", "") + "_" +
                            currentQuestion[0] + "_" +
                            new SimpleDateFormat("yy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date()) + "_" +
                            1, mAudio.getAudioRecordLimitTime(), mAudio.getAudioSampleRate());
                else
                    AudioRecorder.Start(this, mAudio.getAudioRecordQuestions().get(number), mAudio.getAudioRecordLimitTime(), mAudio.getAudioSampleRate());
            }
    }

    private void createPhoto() {
        if (photoNumber != -1)
            if (photoNumber == number)
                if (photoName == null) {
                    photoName = "photo-" +
                            sharedPreferences.getString("login", "") + "-" +
                            new SimpleDateFormat("yy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date());
                    System.out.println(photoName);
                    new PhotoCamera(this, frameLayout, photoName).createPhoto();
                }
    }

    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        showLocation(locationManager.getLastKnownLocation(provider));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    private void showLocation(Location location) {
        if (location == null)
            return;

        SharedPreferences.Editor editor = sharedPreferences.edit()
                .putString("gps", location.getLatitude() + ":" + location.getLongitude());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, this);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }
}